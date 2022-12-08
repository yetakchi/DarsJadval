from flask import Flask, jsonify, render_template, request, redirect
from dotenv import dotenv_values
from database.database import DataBase
from actions import helper

app = Flask(__name__)
db = DataBase()
env = dict(dotenv_values())


@app.route('/days')
def days():
    result = helper.get_days()
    return jsonify(result)


@app.route('/lessons/auto')
def lessons():
    return redirect(helper.auto_lesson())


@app.route('/lessons/<int:day>')
def current_lesson(day):
    return jsonify(helper.current_lesson(db.lesson(day)))


@app.route('/today')
def today():
    return helper.today_date()


# Templates, admin side
@app.route('/')
def index():
    return render_template('index.html')


@app.route('/about-us')
def about():
    return render_template('about.html')


# Lessons
@app.route('/lessons')
def lesson_list():
    return render_template('lesson/index.html',
                           lessons=helper.lesson_list(db.lessons()),
                           subject_forms=helper.subject_forms
                           )


@app.route('/lessons/create')
@app.route('/lessons/<int:lesson>/detail')
def add_lesson(lesson=0):
    data = {
        'subjects': db.subjects(),
        'teachers': db.teachers(),
        'days': helper.days,
        'subject_forms': helper.subject_forms,
        'lesson': db.lesson_detail(lesson) if lesson else {},
    }

    return render_template("lesson/form.html", **data)


@app.route('/lessons', methods=['POST', 'PUT'])
def upsert_lesson():
    if request.method == 'PUT':
        return redirect('/lessons')
    return redirect(db.add_lesson(request.form))


@app.route('/lessons/<int:lesson>/delete', methods=['DELETE'])
def delete_lesson(lesson):
    return redirect(db.delete_lesson(lesson))


# Subjects
@app.route('/subjects')
def subject_list():
    return render_template('subject/index.html', subjects=db.subjects())


@app.route('/subjects/create')
@app.route('/subjects/<int:subject>/detail')
def create_subject(subject=0):
    if subject:
        subject = db.subject_detail(subject)
    else:
        subject = {}
    return render_template('subject/form.html', forms=helper.subject_forms, subject=subject)


@app.route('/subjects', methods=['POST', 'PUT'])
def upsert_subject():
    return redirect(db.add_subject(request.form))


@app.route('/subjects/<int:subject>', methods=['DELETE'])
def delete_subject(subject):
    return db.delete_subject(subject)


# Teachers
@app.route('/teachers/create', methods=['GET', 'POST'])
def add_teacher():
    if request.method == 'GET':
        return render_template('teacher/form.html')

    return redirect(db.add_teacher(request.form))


@app.route('/teachers')
def teacher_list():
    return render_template('teacher/index.html', teachers=db.teachers())


@app.route('/teachers/<int:teacher>/detail')
def teacher_detail(teacher):
    return render_template('teacher/detail.html', teacher=db.teacher_detail(teacher))


@app.route('/teachers/<int:teacher>', methods=['PUT'])
def teacher_update(teacher):
    return redirect(db.update_teacher(teacher, request.form))


@app.route('/teachers/<int:teacher>', methods=['DELETE'])
def teacher_delete(teacher):
    return redirect(db.delete_teacher(teacher))


@app.errorhandler(404)
def not_found(error):
    return render_template('errors/404.html', error=error), 404


if __name__ == '__main__':
    app.run(debug=True, host=env['SERVER_HOST'], port=env['SERVER_PORT'])  # $ flask run --host=0.0.0.0 (192.168.1.x)

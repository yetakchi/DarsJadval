from flask import Flask, jsonify, render_template, request, redirect
from database.database import DataBase
from actions import helper

app = Flask(__name__)
db = DataBase()


@app.route('/days')
def days():
    result = helper.get_days()
    return jsonify(result)


@app.route('/lessons/auto')
def lessons():
    return redirect(helper.auto_lesson())


@app.route('/lessons/<int:day>')
def current_lesson(day):
    lessons_list = helper.current_lesson(db.lesson(day))
    return jsonify(lessons_list)


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
@app.route('/lessons/create', methods=["GET", "POST"])
def add_lesson():
    if request.method == 'POST':
        return redirect(db.add_lesson(request.form))

    subjects = db.subjects()
    helper.check_subject_form(subjects)
    return render_template("lesson/form.html", subjects=subjects, teachers=db.teachers(), days=helper.days)


@app.route('/lessons')
def lesson_list():
    return render_template('lesson/index.html', lessons=helper.lesson_list(db.lessons()))


@app.route('/lessons/<int:lesson>/detail')
def lesson_detail(lesson):
    return render_template('lesson/detail.html', lesson=db.lesson_detail(lesson))


@app.route('/lessons/<int:lesson>/delete', methods=['POST'])
def delete_lesson(lesson):
    return redirect(db.delete_lesson(lesson))


# Subjects
@app.route('/subjects')
def subject_list():
    return render_template('subject/index.html', subjects=db.subjects())


@app.route('/subjects/create', methods=['GET', 'POST'])
def create_subject():
    if request.method == 'GET':
        return render_template('subject/form.html')

    return redirect(db.add_subject(request.form))


@app.route('/subjects/<int:subject>/detail')
def subject_detail(subject):
    return db.subject_detail(subject)


@app.route('/subjects/<int:subject>/delete', methods=['POST'])
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


@app.route('/teachers/<int:teacher>/delete', methods=['POST'])
def teacher_delete(teacher):
    return redirect(db.delete_teacher(teacher))


@app.route('/teachers/<int:teacher>/update', methods=['POST'])
def teacher_update(teacher):
    return redirect(db.update_teacher(teacher, request.form))


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')  # $ flask run --host=0.0.0.0 (192.168.1.x)

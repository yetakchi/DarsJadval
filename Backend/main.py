from flask import Flask, jsonify, render_template, request, redirect, url_for

from actions import helper
from app.repositories import TeacherRepository, SubjectRepository, LessonRepository
from config import SERVER_HOST, SERVER_PORT
from database import immutable

app = Flask(__name__)

teacher_repo = TeacherRepository()
subject_repo = SubjectRepository()
lesson_repo = LessonRepository()


@app.before_request
def hook():
    if request.form.get("_method"):
        request.method = request.form.get("_method")
        request.environ['REQUEST_METHOD'] = request.form.get("_method")


@app.route('/days')
def days():
    result = helper.get_days()
    return jsonify(result)


@app.get('/lessons/auto')
def lessons():
    weekday = helper.auto_lesson()
    return redirect(f"/lessons/{weekday + 1}")


@app.get('/lessons/<int:day>')
def current_lesson(day):
    return jsonify(helper.current_lesson(lesson_repo.get_today_lessons(day)))


@app.get('/today')
def today():
    return helper.today_date()


# Templates, admin side
@app.get('/')
def index():
    return render_template('index.html')


@app.get('/about-us')
def about():
    return render_template('about.html')


# Lessons
@app.get('/lessons')
def lesson_list():
    return render_template('lesson/index.html',
                           lessons=helper.lesson_list(lesson_repo.lessons()),
                           subject_forms=immutable.subject_forms
                           )


@app.get('/lessons/create')
@app.get('/lessons/<int:idx>/detail')
def lesson_addup(idx=0):
    data = {
        'subjects': subject_repo.all(),
        'teachers': teacher_repo.all(),
        'days': immutable.days,
        'subject_forms': immutable.subject_forms,
        'action': 'lessons'
    }
    if idx:
        data.update({
            'lesson': lesson_repo.get(idx),
            'action': url_for('lesson_update_delete', idx=idx)
        })
    else:
        data.update({'lesson': {}})

    return render_template("lesson/form.html", **data)


@app.post('/lessons')
def lesson_create():
    lesson_repo.create(request.form)
    return redirect('/lessons')


@app.route('/lessons/<int:idx>', methods=['PUT', 'DELETE', 'POST'])
def lesson_update_delete(idx):
    if request.method == 'PUT':
        lesson_repo.update(idx, dict(request.form))
    else:
        lesson_repo.delete(idx)

    return redirect('/lessons')


# Subjects
@app.route('/subjects')
def subject_list():
    return render_template('subject/index.html', subjects=subject_repo.all())


@app.get('/subjects/create')
@app.post('/subjects')
def subject_create():
    if request.method == 'GET':
        return render_template('subject/form.html',
                               forms=immutable.subject_forms, subject={},
                               action=url_for('subject_create', _method="POST")
                               )
    subject_repo.create(request.form)
    return redirect('/subjects')


@app.get('/subjects/<int:idx>')
def subject_detail(idx):
    subject = subject_repo.get(idx)
    return render_template('subject/form.html', forms=immutable.subject_forms,
                           subject=subject, action=url_for('subject_update_delete', idx=idx))


@app.route('/subjects/<int:idx>', methods=['PUT', 'DELETE', 'POST'])
def subject_update_delete(idx):
    if request.method == "PUT":
        subject_repo.update(idx, request.form)
    else:
        subject_repo.delete(idx)

    return redirect('/subjects')


# Teachers
@app.get('/teachers')
def teacher_list():
    return render_template('teacher/index.html', teachers=teacher_repo.all())


@app.get('/teachers/create')
@app.post('/teachers')
def teacher_add():
    if request.method == 'GET':
        return render_template('teacher/form.html')

    teacher_repo.create(request.form)
    return redirect('/teachers')


@app.get('/teachers/<int:idx>')
def teacher_detail(idx):
    return render_template('teacher/detail.html', teacher=teacher_repo.get(idx))


@app.route('/teachers/<int:idx>', methods=['PUT', 'DELETE', 'POST'])
def teacher_update_delete(idx):
    if request.method == "PUT":
        teacher_repo.update(idx, request.form)
    else:
        teacher_repo.delete(idx)

    return redirect('/teachers')


@app.errorhandler(404)
def not_found(error):
    return render_template('errors/404.html', error=error), 404


if __name__ == '__main__':
    # $ flask run --host=0.0.0.0 (192.168.1.x)
    app.run(debug=True, host=SERVER_HOST, port=SERVER_PORT)

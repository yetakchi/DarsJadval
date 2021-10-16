from flask import Flask, jsonify, render_template, request, redirect
from database import DataBase
import helper  # from helper import *

app = Flask(__name__)
db = DataBase()


@app.route('/days')
def Days():
    result = helper.getDays()

    return jsonify(result)


@app.route('/lessons/auto')
def Lessons():
    return redirect(helper.autoLesson())


@app.route('/lessons/<int:day>')
def Lesson(day):
    lessons_list = helper.thisLessons(db.Lesson(day))
    return jsonify(lessons_list)


@app.route('/today')
def Today():
    return helper.todayDate()


# Templates, admin side
@app.route('/')
def index():
    return render_template('index.html')


@app.route('/about-us')
def about():
    return render_template('about.html')


# Lessons
@app.route('/lessons/create', methods=["GET", "POST"])
def addLesson():
    if request.method == 'POST':
        return redirect(db.addLesson(request.form))

    subjects = db.Subjects()
    helper.checkSubjectForm(subjects)
    return render_template("create_lesson.html", subjects=subjects, teachers=db.Teachers(), days=helper.days)


@app.route('/lessons')
def lessonsList():
    lessons = helper.lessonsList(db.Lessons())
    return render_template('lessons.html', lessons=lessons)


@app.route('/lessons/<int:lesson>/detail')
def lessonDetail(lesson):
    return render_template('detail_lesson.html', lesson=db.lessonDetail(lesson))


@app.route('/lessons/<int:lesson>/delete', methods=['POST'])
def deleteLesson(lesson):
    return redirect(db.deleteLesson(lesson))


# Subjects
@app.route('/subjects')
def subjectsList():
    return render_template('subjects.html', subjects=db.Subjects())


@app.route('/subjects/create', methods=['GET', 'POST'])
def createSubject():
    if request.method == 'GET':
        return render_template('create_subject.html')

    return redirect(db.addSubject(request.form))


@app.route('/subjects/<int:subject>/detail')
def subjectDetail(subject):
    return db.subjectDetail(subject)


@app.route('/subjects/<int:subject>/delete', methods=['POST'])
def deleteSubject(subject):
    return db.deleteSubject(subject)


# Teachers
@app.route('/teachers/create', methods=['GET', 'POST'])
def addTeacher():
    if request.method == 'GET':
        return render_template('create_teacher.html')

    return redirect(db.addTeacher(request.form))


@app.route('/teachers')
def teachersList():
    return render_template('teachers.html', teachers=db.Teachers())


@app.route('/teachers/<int:teacher>/detail')
def teacherDetail(teacher):
    return render_template('detail_teacher.html', teacher=db.teacherDetail(teacher))


@app.route('/teachers/<int:teacher>/delete', methods=['POST'])
def teacherDelete(teacher):
    return redirect(db.deleteTeacher(teacher))


@app.route('/teachers/<int:teacher>/update', methods=['POST'])
def teacherUpdate(teacher):
    return redirect(db.updateTeacher(teacher, request.form))


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')  # $ flask run --host=0.0.0.0 (192.168.1.x)

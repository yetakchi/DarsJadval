from connector import MYSQLConnector


class DataBase(MYSQLConnector):
    # Lesson
    def Lessons(self):
        query = "SELECT lessons.id, lessons.teach_form, lessons.couple, lessons.week_day, lessons.room, " \
                "subjects.subject, subjects.form, teachers.teacher " \
                "FROM lessons, subjects, teachers " \
                "WHERE lessons.subject = subjects.id AND lessons.teacher = teachers.id;"
        return self.Select(query)

    def Lesson(self, day):
        query = "SELECT lessons.id, lessons.teach_form, lessons.couple, lessons.week_day, lessons.room, " \
                "subjects.subject, subjects.form, teachers.teacher " \
                "FROM lessons, subjects, teachers " \
                f"WHERE week_day = {day} AND lessons.subject = subjects.id AND lessons.teacher = teachers.id;"
        return self.Select(query)

    def addLesson(self, request):
        query = "INSERT INTO lessons(subject, teacher, teach_form, couple, week_day, room)" \
                " VALUES(%s, %s, %s, %s, %s, %s)"
        return self.Insert(query, (request['subject'], request['teacher'], request['teach_form'],
                                   request['couple'], request['week_day'], request['room']),
                           reply='/lessons'
                           )

    def lessonDetail(self, lesson):
        query = f"SELECT * FROM lessons WHERE id = {lesson}"
        return self.selectOne(query)

    def deleteLesson(self, lesson):
        return self.Delete("lessons", lesson)

    # Subject
    def Subjects(self):
        return self.SelectTable("subjects")

    def addSubject(self, req):
        query = "INSERT INTO subjects(subject, form) VAlUES(%s, %s);"
        return self.Insert(query, (req['subject'], req['form']), '/subjects')

    def subjectDetail(self, subject):
        query = f"SELECT * FROM subjects WHERE id = {subject}"
        return self.selectOne(query)

    def deleteSubject(self, subject):
        return self.Delete("subjects", subject)

    # Teacher
    def Teachers(self):
        return self.SelectTable("teachers")

    def addTeacher(self, data):
        return self.Insert(f"INSERT INTO teachers(teacher) VALUES('{data['teacher']}');", reply='/teachers')

    def teacherDetail(self, teacher):
        query = f"SELECT * FROM teachers WHERE id = {teacher}"
        return self.selectOne(query)

    def deleteTeacher(self, teacher):
        return self.Delete("teachers", teacher)

    def updateTeacher(self, teacher, data):
        query = f"UPDATE teachers SET teacher = '{data['teacher']}' WHERE id = {teacher};"
        return self.Update(query, reply='/teachers')

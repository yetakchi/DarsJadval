from .connector import MYSQLConnector


class DataBase(MYSQLConnector):
    # Lesson
    def lessons(self):
        query = "SELECT lessons.id, lessons.teach_form, lessons.couple, lessons.week_day, lessons.room, " \
                "subjects.name as subject, subjects.form, teachers.name as teacher " \
                "FROM lessons, subjects, teachers " \
                "WHERE lessons.subject_id = subjects.id AND lessons.teacher_id = teachers.id;"
        return self.select(query)

    def lesson(self, day):
        query = "SELECT lessons.id, lessons.teach_form, lessons.couple, lessons.week_day, lessons.room, " \
                "subjects.name as subject, subjects.form, teachers.name as teacher " \
                "FROM lessons, subjects, teachers " \
                f"WHERE week_day = {day} AND lessons.subject_id = subjects.id AND lessons.teacher_id = teachers.id;"
        return self.select(query)

    def add_lesson(self, request):
        query = "INSERT INTO lessons(subject_id, teacher_id, teach_form, couple, week_day, room)" \
                " VALUES(%s, %s, %s, %s, %s, %s)"
        return self.insert(query, (request['subject_id'], request['teacher_id'], request['teach_form'],
                                   request['couple'], request['week_day'], request['room']),
                           reply='/lessons'
                           )

    def lesson_detail(self, lesson):
        query = f"SELECT * FROM lessons WHERE id = {lesson}"
        return self.select_one(query)

    def delete_lesson(self, lesson):
        return self.delete("lessons", lesson)

    # Subject
    def subjects(self):
        return self.select_table("subjects")

    def add_subject(self, req):
        query = "INSERT INTO subjects(name, form) VAlUES(%s, %s);"
        return self.insert(query, (req['name'], req['form']), '/subjects')

    def subject_detail(self, subject):
        query = f"SELECT * FROM subjects WHERE id = {subject}"
        return self.select_one(query)

    def delete_subject(self, subject):
        return self.delete("subjects", subject)

    # Teacher
    def teachers(self):
        return self.select_table("teachers")

    def add_teacher(self, data):
        return self.insert(f"INSERT INTO teachers(name) VALUES('{data['name']}');", reply='/teachers')

    def teacher_detail(self, teacher):
        query = f"SELECT * FROM teachers WHERE id = {teacher}"
        return self.select_one(query)

    def delete_teacher(self, teacher):
        return self.delete("teachers", teacher)

    def update_teacher(self, teacher, data):
        query = f"UPDATE teachers SET name = '{data['name']}' WHERE id = {teacher};"
        return self.update(query, reply='/teachers')

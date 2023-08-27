from typing import Optional, List, Union

from mysql.connector.errors import ProgrammingError
from werkzeug.datastructures import ImmutableMultiDict

from database import MYSQLConnector
from .CRUDRepository import CRUDRepository


class UserRepository(CRUDRepository, MYSQLConnector):
    def all(self) -> List[Union[dict, tuple]]:
        self.connect()
        query = "SELECT * FROM users;"
        self.cursor.execute(query)
        entities = self.cursor.fetchall()
        self.disconnect()
        return entities

    def create(self, form: dict) -> Union[dict, tuple]:
        self.connect()
        query = "INSERT INTO users(`name`, `surname`, `username`, `password`, `phone`) " \
                "VALUES (%s, %s, %s, %s, %s);"
        self.cursor.execute(query, tuple(form.values()))
        self.disconnect().commit()

        # query = f"SELECT * FROM users WHERE `id` = {self.mb.lastrowid}"
        # self.mb.execute(query)
        entity = form.copy()
        entity['id'] = self.cursor.lastrowid
        self.disconnect()
        return entity

    def get(self, nd: int) -> Optional[Union[dict, tuple]]:
        self.connect()
        query = "SELECT * FROM users WHERE id = %s;"
        self.cursor.execute(query, (nd,))
        entity: dict = self.cursor.fetchone()
        self.disconnect()

        try:
            entity.pop("password")
        except AttributeError:
            pass
        return entity

    def update(self, idx: int, form: dict) -> Union[dict, tuple]:
        try:
            self.connect()
            query = "UPDATE users SET " \
                    "name = %s, surname = %s, patronymic = %s, username = %s, email = %s, phone = %s " \
                    "WHERE id = {};".format(idx)
            self.cursor.execute(query, tuple(form.values()))
            self.connection.commit()
            self.disconnect()
            entity = {'id': idx}
            entity.update(form)
            return entity
        except ProgrammingError as e:
            raise e

    def delete(self, nd) -> Optional[Union[dict, tuple]]:
        self.connect()
        query = "DELETE FROM users WHERE id = {};".format(nd)
        self.cursor.execute(query)
        self.connection.commit()
        self.disconnect()
        return None


class TeacherRepository(CRUDRepository, MYSQLConnector):

    def all(self) -> List[Union[dict, tuple]]:
        return self.select_from_table("teachers")

    def create(self, form: dict) -> Union[dict, tuple]:
        return self.insert(f"INSERT INTO teachers(name) VALUES('{form['name']}');")

    def get(self, idx: int) -> Optional[Union[dict, tuple]]:
        query = f"SELECT * FROM teachers WHERE id = {idx}"
        return self.select_one(query)

    def update(self, idx: int, form: dict) -> Union[dict, tuple]:
        query = f"UPDATE teachers SET name = '{form['name']}' WHERE id = {idx};"
        return super().update_in(query)

    def delete(self, idx: int) -> Optional[Union[dict, tuple]]:
        return super().delete_from("teachers", idx)


class SubjectRepository(CRUDRepository, MYSQLConnector):

    def all(self) -> List[Union[dict, tuple]]:
        return self.select_from_table("subjects")

    def create(self, form: dict) -> Union[dict, tuple]:
        query = "INSERT INTO subjects(name, form) VAlUES(%s, %s);"
        return self.insert(query, (form['name'], form['form']))

    def get(self, idx: int) -> Optional[Union[dict, tuple]]:
        query = f"SELECT * FROM subjects WHERE id = {idx}"
        return self.select_one(query)

    def update(self, idx: int, form: ImmutableMultiDict) -> Union[dict, tuple]:
        query = f"UPDATE subjects set name = %s, form = %s WHERE id = {idx}"
        self.update_in(query, (form['name'], form['form']))
        form = dict(form)
        form.setdefault('id', idx)
        return form

    def delete(self, idx: int) -> Optional[Union[dict, tuple]]:
        return self.delete_from("subjects", idx)


class LessonRepository(CRUDRepository, MYSQLConnector):

    def all(self) -> List[Union[dict, tuple]]:
        return self.select_from_table("lessons")

    def create(self, form: dict) -> Union[dict, tuple]:
        query = "INSERT INTO lessons(subject_id, teacher_id, teach_form, couple, week_day, room)" \
                " VALUES(%s, %s, %s, %s, %s, %s)"
        return self.insert(query, (
            form['subject_id'], form['teacher_id'], form['teach_form'],
            form['couple'], form['week_day'], form['room']
        ))

    def get(self, idx: int) -> Optional[Union[dict, tuple]]:
        query = f"SELECT * FROM lessons WHERE id = {idx}"
        return self.select_one(query)

    def update(self, idx: int, form: dict) -> Union[dict, tuple]:
        query = "UPDATE lessons SET subject_id = %s, teacher_id = %s, teach_form = %s, " \
                "couple = %s, week_day = %s, room = %s " \
                f"WHERE id = {idx};"

        form.pop("_method")
        return self.update_in(query, tuple(form.values()))

    def delete(self, idx: int) -> Optional[Union[dict, tuple]]:
        return self.delete_from("lessons", idx)

    # Lesson
    def lessons(self):
        query = "SELECT lessons.id, lessons.teach_form, lessons.couple, lessons.week_day, lessons.room, " \
                "subjects.name as subject, subjects.form, teachers.name as teacher " \
                "FROM lessons, subjects, teachers " \
                "WHERE lessons.subject_id = subjects.id AND lessons.teacher_id = teachers.id;"
        return self.select(query)

    def get_today_lessons(self, day):
        query = "SELECT lessons.id, lessons.teach_form, lessons.couple, lessons.week_day, lessons.room, " \
                "subjects.name as subject, subjects.form, teachers.name as teacher " \
                "FROM lessons, subjects, teachers " \
                f"WHERE week_day = {day} AND lessons.subject_id = subjects.id AND lessons.teacher_id = teachers.id;"
        return self.select(query)

import calendar
import datetime

from database.immutable import *


# Lessons
def auto_lesson():
    time = datetime.datetime.now()
    return time.weekday()


def current_lesson(lessons):
    t = f"{datetime.datetime.now().time()}"

    for lesson in lessons:
        lesson['form'] = subject_forms[lesson['form']]
        couple(lesson)

        lesson['active'] = lesson['start_time'] < t < lesson['end_time']
        lesson['isLast'] = lesson == lessons[-1]
    return lessons


def lesson_list(lessons):
    for les in lessons:
        couple(les)

    return lessons


# Date
def get_days():
    time = datetime.datetime.now()
    today = time.weekday() + 1
    first = 0
    week_days = check_week_date(time)

    for day in days:
        day['active'] = day['id'] == today
        day['day'] = week_days[first]
        first += 1

    return days


def check_week_date(time):
    month_days = calendar.Calendar().monthdayscalendar(time.now().year, time.now().month)
    week = (time.day + 6) // 7
    this_week = month_days[week - 1]

    # Convert to date
    day = 1
    for i, date in enumerate(this_week):
        if date == 0:
            this_week[i] = f'0{day}'
            day += 1

    return this_week


def today_date():
    time = datetime.datetime.now()
    return str(time.day) + " - " + months[time.month - 1]


def couple(lesson):
    lesson['start_time'] = couples[lesson['couple'] - 1]['start_time']
    lesson['end_time'] = couples[lesson['couple'] - 1]['end_time']

    # lesson['start_time'] = ('0' + str(lesson['start_time']))[-8:-3] # timedelta to clock string
    # lesson['end_time'] = ('0' + str(lesson['end_time']))[-8:-3]

import datetime

from database.immutable import subject_forms, couples


# Lessons
def formify_list(lessons):
    for les in lessons:
        couple(les)

    return lessons


def current_lesson(lessons):
    t = f"{datetime.datetime.now().time()}"

    for lesson in lessons:
        lesson['form'] = subject_forms[lesson['form']]
        couple(lesson)

        lesson['active'] = lesson['start_time'] < t < lesson['end_time']
        lesson['isLast'] = lesson == lessons[-1]
    return lessons


def couple(lesson):
    lesson['start_time'] = couples[lesson['couple'] - 1]['start_time']
    lesson['end_time'] = couples[lesson['couple'] - 1]['end_time']

    # lesson['start_time'] = ('0' + str(lesson['start_time']))[-8:-3] # timedelta to clock string
    # lesson['end_time'] = ('0' + str(lesson['end_time']))[-8:-3]

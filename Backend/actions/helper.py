import calendar
import datetime


# Variables
days = [
      {
        "id": 1,
        "day_name": "Dush",
        "full_name": "Dushanba"
      },
      {
        "id": 2,
        "day_name": "Sesh",
        "full_name": "Seshanba"
      },
      {
        "id": 3,
        "day_name": "Chor",
        "full_name": "Chorshanba"
      },
      {
        "id": 4,
        "day_name": "Pay",
        "full_name": "Payshanba"
      },
      {
        "id": 5,
        "day_name": "Jum",
        "full_name": "Juma"
      },
      {
        "id": 6,
        "day_name": "Shan",
        "full_name": "Shanba"
      }
    ]


# Lessons
def auto_lesson():
    time = datetime.datetime.now()
    return f"/lessons/{time.weekday() + 1}"


def current_lesson(lessons):
    t = f"{datetime.datetime.now().time()}"

    for lesson in lessons:
        form(lesson)
        couple(lesson)

        lesson['active'] = lesson['start_time'] < t < lesson['end_time']
        # lesson['isLast'] = lesson == lessons[-1]
    return lessons


def lesson_list(lessons):
    for les in lessons:
        form(les)
        couple(les)

    return lessons


def check_subject_form(subjects):
    for sub in subjects:
        form(sub)


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
    months = [
        'yanvar',
        'fevral',
        'mart',
        'aprel',
        'may',
        'iyun',
        'iyul',
        'avgust',
        'sentyabr',
        'oktyabr',
        'noyabr',
        'dekabr'
    ]

    time = datetime.datetime.now()
    return str(time.day) + " - " + months[time.month - 1]


# Private functions
def form(item):
    if item['form'] == 2:
        item['form'] = "Amaliyot"
    elif item['form'] == 3:
        item['form'] = "Tajriba"
    elif item['form'] == 4:
        item['form'] = "Seminar"
    elif item['form'] == 0:
        item['form'] = ""
    else:
        item['form'] = "Ma\u2019ruza"


def couple(lesson):
    if lesson['couple'] == 1:
        lesson['start_time'] = '08:30'
        lesson['end_time'] = '09:50'
    elif lesson['couple'] == 2:
        lesson['start_time'] = '10:00'
        lesson['end_time'] = '11:20'
    elif lesson['couple'] == 3:
        lesson['start_time'] = '11:30'
        lesson['end_time'] = '12:50'
    elif lesson['couple'] == 4:
        lesson['start_time'] = '13:30'
        lesson['end_time'] = '14:50'
    elif lesson['couple'] == 5:
        lesson['start_time'] = '15:00'
        lesson['end_time'] = '16:20'
    elif lesson['couple'] == 6:
        lesson['start_time'] = '16:30'
        lesson['end_time'] = '17:50'

    # lesson['start_time'] = ('0' + str(lesson['start_time']))[-8:-3] # timedelta to clock string
    # lesson['end_time'] = ('0' + str(lesson['end_time']))[-8:-3]

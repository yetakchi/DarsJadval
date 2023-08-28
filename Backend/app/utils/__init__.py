import calendar
from datetime import datetime

from database.immutable import months, days


def today_date():
    time = datetime.now()
    return str(time.day) + " - " + months[time.month - 1]


def day_of_week():
    time = datetime.now()
    return time.weekday()


# Date
def get_days():
    time = datetime.now()
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

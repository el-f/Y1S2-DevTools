from flask_wtf import FlaskForm
from wtforms import StringField, BooleanField, SubmitField, SelectField
from wtforms.validators import ValidationError
from wtforms.fields.html5 import DateField
from datetime import date, timedelta


# noinspection PyMethodMayBeStatic
class FlightsForm(FlaskForm):
    direction = SelectField(
        'Direction',
        choices=[
            ("all", "all"),
            ("departures", "departures"),
            ("arrivals", "arrivals")
        ]
    )
    country = StringField('Country')
    city = StringField('City')
    airport = StringField('Airport')
    airline = StringField('Airline')
    date1 = DateField('Start Date', default=date.today())
    date2 = DateField('End Date', default=date.today() + timedelta(100))
    sunday = BooleanField('Sunday', default=True)
    monday = BooleanField('Monday', default=True)
    tuesday = BooleanField('Tuesday', default=True)
    wednesday = BooleanField('Wednesday', default=True)
    thursday = BooleanField('Thursday', default=True)
    friday = BooleanField('Friday', default=True)
    saturday = BooleanField('Saturday', default=True)

    submit = SubmitField('Show Filtered Flights')

    def validate_date1(self, field):
        if field.data < date.today():
            raise ValidationError("Start date must not be earlier than today.")

    def validate_date2(self, field):
        if field.data > date.today() + timedelta(100):
            raise ValidationError("End date must not be too far in the future.")

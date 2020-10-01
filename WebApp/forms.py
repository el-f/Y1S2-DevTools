from flask_wtf import FlaskForm
from wtforms import StringField, BooleanField, SubmitField, SelectField
from wtforms.validators import ValidationError, Optional
from wtforms.fields.html5 import DateField, IntegerField
from datetime import date, timedelta


# noinspection PyMethodMayBeStatic
class FlightsForm(FlaskForm):
    direction = SelectField(
        'Direction',
        choices=[
            ("all", "All"),
            ("departures", "Departures"),
            ("arrivals", "Arrivals")
        ]
    )
    terminal = IntegerField(
        'Terminal',
        render_kw={"placeholder": "Valid #: 1-3"},
        validators=[Optional(strip_whitespace=True)]
    )
    country = StringField('Country')
    city = StringField('City')
    airport = StringField('Airport')
    airline = StringField('Airline')
    date1 = DateField('Start Date', default=date.today())
    date2 = DateField('End Date', default=date.today() + timedelta(100))
    sunday = BooleanField('Sun', default=True)
    monday = BooleanField('Mon', default=True)
    tuesday = BooleanField('Tue', default=True)
    wednesday = BooleanField('Wed', default=True)
    thursday = BooleanField('Thu', default=True)
    friday = BooleanField('Fri', default=True)
    saturday = BooleanField('Sat', default=True)

    submit = SubmitField('Show Filtered Flights')

    def validate_terminal(self, field):
        if field.data < 1 or field.data > 3:
            raise ValidationError("Invalid Terminal Number! (Valid Terminals: 1-3)")

    def validate_date1(self, field):
        start_date = date.fromisoformat("2020-01-01")
        if not field.data:
            raise ValidationError(" - This Field Must Be Filled!")
        if field.data < start_date:
            raise ValidationError(f"Start Date Must Not Be Earlier Than {start_date}")

    def validate_date2(self, field):
        if not field.data:
            raise ValidationError(" - This Field Must Be Filled!")
        end_date = date.today() + timedelta(100)
        if field.data > end_date:
            raise ValidationError("End Date Must Not Be More Than 100 Days From Today")
        if self.date1.data and field.data < self.date1.data:
            raise ValidationError("End Date Must Be After Start Date")

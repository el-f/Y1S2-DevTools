from flask_wtf import FlaskForm
from wtforms import StringField, BooleanField, SubmitField
from wtforms.validators import data_required, Length, Optional, ValidationError
from wtforms.fields.html5 import DateField
from datetime import date, timedelta


class FlightsForm(FlaskForm):
    direction = StringField('direction', validators=[data_required(), Length(min=3, max=11)])
    country = StringField('country')
    city = StringField('city')
    airport = StringField('airport')
    airline = StringField('airline')
    date1 = DateField('date1', default=date.today(), validators=[Optional()])
    date2 = DateField('date2', default=date.today() + timedelta(100), validators=[Optional()])
    sunday = BooleanField('sunday')
    monday = BooleanField('monday')
    tuesday = BooleanField('tuesday')
    wednesday = BooleanField('wednesday')
    thursday = BooleanField('thursday')
    friday = BooleanField('friday')
    saturday = BooleanField('saturday')

    submit = SubmitField('Submit')

    def validate_date1(self, field):
        if field.data < date.today():
            raise ValidationError("Start date must not be earlier than today.")

    def validate_date2(self, field):
        if field.data > date.today() + timedelta(120):
            raise ValidationError("Start date must not be too far in the future.")

#!/usr/bin/python
import subprocess

from flask import Flask, request

app = Flask("my_app1")


@app.route("/")
def airport():
    return subprocess.check_output(["java", "-classpath", "C:/Users/Elazar/Documents/GitHub/Y1S2-Homework/bin",
                                    "AirportProject.Program",
                                    request.args.get('d'),  # direction
                                    request.args.get('cnt'),  # country
                                    request.args.get('cty'),  # city
                                    request.args.get('ap'),  # airport
                                    request.args.get('trm'),  # terminal
                                    request.args.get('wd'),  # dayofweek
                                    request.args.get('start'),  # date range lower bound
                                    request.args.get('end')  # date range higher bound
                                    ])


app.run(port=8000, host="0.0.0.0")

# http://localhost:8000/?d=departures&cnt=&cty=&ap=&trm=&wd=&start=&end=2020/11/21-22:22:22
# http://localhost:8000/?d=&cnt=&cty=&ap=&trm=&wd=&start=&end=

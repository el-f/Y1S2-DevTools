#!/usr/bin/python
import subprocess

from flask import Flask, request

app = Flask("my_app1")


@app.route("/")
def airport():
    return subprocess.check_output(["java", "-classpath", "C:/Users/Elazar/Documents/GitHub/Y1S2-Homework/bin",
                                    "AirportProject.Program",
                                    request.args.get('d'),  # direction
                                    # request.args.get('cnt'),  # country
                                    # request.args.get('cty'),  # city
                                    # request.args.get('ap'),   # airport
                                    # request.args.get('trm'),  # terminal
                                    # request.args.get('wd'),   # dayofweek
                                    # request.args.get('rng')   # date range
                                    ])


app.run(port=8000, host="0.0.0.0")

# http://localhost:8000/?d=arrivals
# http://127.0.0.1:5000/?d=arrivals

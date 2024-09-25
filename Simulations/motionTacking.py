import os, requests
from datetime import datetime
import random

def generate_motion_data(num):
    if num < 1: num = 1
    if num > 146: num = 146  # Limiting the number of records for simplicity
    
    count = 0
    for x in range(0, num):
        new_data = {
            'trackingID': x + 1,  # Unique identifier for motion data
            'exerciseID': round(random.uniform(1, 10), 2),  # Random exercise ID as a float
            'jointAngle': round(random.uniform(0, 180), 2),  # Joint angle in degrees
            'rangeOfMotion': round(random.uniform(10, 180), 2),  # Range of motion in degrees
            'movementSpeed': round(random.uniform(0.1, 2.5), 2),  # Speed in meters per second
            'stepLength': round(random.uniform(0.3, 1.5), 2),  # Step length in meters
            'cadence': round(random.uniform(60, 150), 2),  # Steps per minute
            'timestamp': datetime.now().isoformat()  # Current timestamp
        }
        
        # Send POST request to the database
        r = requests.post(os.getenv('DB_URL') + "/motiondata", json=new_data)
        if r.status_code == 201:
            count += 1

    return count
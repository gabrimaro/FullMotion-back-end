import os, requests
import random
from datetime import datetime

# Example set of possible values for body alignment, center of mass, and sway patterns
body_alignment_options = [
    "Neutral", "Forward", "Backward", "Left Tilt", "Right Tilt"
]
center_of_mass_options = [
    "Center", "Forward Shift", "Backward Shift", "Left Shift", "Right Shift"
]
sway_pattern_options = [
    "Stable", "Mild Sway", "Moderate Sway", "Significant Sway"
]

def generate_balance_data(num):
    if num < 1: num = 1
    if num > 146: num = 146  # Limiting the number of records for simplicity
    
    count = 0
    for x in range(0, num):
        new_data = {
            'balanceID': x + 1,  # Unique identifier for balance data
            'sessionID': random.randint(1, 100),  # Random session ID as an integer
            'bodyAlignment': random.choice(body_alignment_options),  # Random body alignment description
            'centerOfMass': random.choice(center_of_mass_options),  # Random center of mass description
            'swayPattern': random.choice(sway_pattern_options),  # Random sway pattern description
            'balanceTime': round(random.uniform(5.0, 60.0), 2),  # Balance time in seconds
            'supportNeeded': random.choice([True, False]),  # Boolean for support needed
        }
        
        # Send POST request to the database
        r = requests.post(os.getenv('DB_URL') + "/balancedata", json=new_data)
        if r.status_code == 201:
            count += 1

    return count
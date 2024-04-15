import requests

# Replace with your OpenWeatherMap API key and garden location
api_key = 'cbac092f192014ba5a26b90ecab6abba'
city = input("Enter Your City Name to Find Out Your Garden Maintain : ")


# Function to fetch weather data from OpenWeatherMap API
def fetch_weather_data():
    base_url = 'https://api.openweathermap.org/data/2.5/weather'
    params = {
        'q':{city},
        'appid': api_key,
        'units': 'metric',  # You can change units to 'imperial' for Fahrenheit
    }

    response = requests.get(base_url, params=params)
    data = response.json()
    return data

# Function to check weather conditions and generate alerts
def check_garden_conditions(data):
    alerts = []

    temperature = data['main']['temp']
    humidity = data['main']['humidity']
    precipitation = data.get('rain', {}).get('1h', 0) + data.get('snow', {}).get('1h', 0)

    if temperature < 10:
        alerts.append('Low temperature: Consider providing heat.')
    elif temperature > 30:
        alerts.append('High temperature: Provide shade or water plants.')

    if humidity < 30:
        alerts.append('Low humidity: Water the garden to increase humidity.')
    elif humidity > 70:
        alerts.append('High humidity: Ensure proper ventilation.')

    if precipitation > 0:
        alerts.append('Precipitation: Take appropriate measures for rain or snow.')

    return alerts

if __name__ == '__main__':
    weather_data = fetch_weather_data()

    if 'main' in weather_data:
        garden_alerts = check_garden_conditions(weather_data)
        if garden_alerts:
            print('Garden Maintenance Alerts:')
            for alert in garden_alerts:
                print('- ' + alert)
        else:
            print('No maintenance alerts at the moment.')
    else:
        print('Failed to fetch weather data. Check your API key and location.')

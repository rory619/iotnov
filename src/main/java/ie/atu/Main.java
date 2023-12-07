/*
 * This ESP32 code is created by esp32io.com
 *
 * This ESP32 code is released in the public domain
 *
 * For more detail (instruction and wiring diagram), visit https://esp32io.com/tutorials/esp32-ultrasonic-sensor-piezo-buzzer
 */
#include <Wire.h>              //lcd
        #include "rgb_lcd.h"           // lcd
        #define TRIG_PIN 26            // ESP32 pin GPIO26 connected to Ultrasonic Sensor's TRIG pin
        #define ECHO_PIN 25            // ESP32 pin GPIO25 connected to Ultrasonic Sensor's ECHO pin
        #define BUZZER_PIN 23          // ESP32 pin GPIO17 connected to Piezo Buzzer's pin
        #define DISTANCE_THRESHOLD 50  // centimeters
        #include <DFRobot_DHT11.h>
        DFRobot_DHT11 DHT;
        #define DHT11_PIN 19
        #include <Servo.h>
static const int SERVO_PIN = 13;
        rgb_lcd lcd;
        const int colorR = 255;  //lcd
        const int colorG = 0;    //lcd
        const int colorB = 0;    //lcd


// variables will change:
        float duration_us, distance_cm;
        Servo servo1;
//----------------------------------------------------
//keypad
        #include <Keypad.h>
        #define ROW_NUM 4     //four rows
        #define COLUMN_NUM 3  //three columns

        char keys[ROW_NUM][COLUMN_NUM] = {
        { '1', '2', '3' },
        { '4', '5', '6' },
        { '7', '8', '9' },
        { '*', '0', '#' },
        };
        byte pin_rows[ROW_NUM] = { 18, 5, 17, 16 };
        byte pin_column[COLUMN_NUM] = { 4, 0, 2 };

        Keypad keypad = Keypad(makeKeymap(keys), pin_rows, pin_column, ROW_NUM, COLUMN_NUM);

        const String password = "7890";  // change your password here
        String input_password;
//--------------------------------------------------------------
//keypad end
        bool alarmArmed = true;


        void setup() {
        // set up the LCD's number of columns and rows:
        lcd.begin(16, 2);

        lcd.setRGB(colorR, colorG, colorB);  //lcd

        // Print a message to the LCD.


        Serial.begin(115200);         // initialize serial port
        pinMode(TRIG_PIN, OUTPUT);    // set ESP32 pin to output mode
        pinMode(ECHO_PIN, INPUT);     // set ESP32 pin to input mode
        pinMode(BUZZER_PIN, OUTPUT);  // set ESP32 pin to output mode
        servo1.attach(SERVO_PIN);
        input_password.reserve(32);  // maximum input characters is 33, change if needed
        delay(1000);
        }

        void loop() {

        if (alarmArmed == true) {

        // generate 10-microsecond pulse to TRIG pin
        digitalWrite(TRIG_PIN, HIGH);
        delayMicroseconds(10);
        digitalWrite(TRIG_PIN, LOW);

        // measure duration of pulse from ECHO pin
        duration_us = pulseIn(ECHO_PIN, HIGH);
        // calculate the distance
        distance_cm = 0.017 * duration_us;
        // print the value to Serial Monitor
        Serial.println(distance_cm);

        if (distance_cm < DISTANCE_THRESHOLD) {
        lcd.clear();
        lcd.setCursor(0, 0);
        lcd.print("Enter password ");


        char key = keypad.getKey();

        if (key) {
        lcd.setCursor(2, 1);
        lcd.print(key);

        if (key == '*') {
        input_password = "";  // clear input password
        } else if (key == '#') {
        if (password == input_password) {
        lcd.print("The password is correct, ACCESS GRANTED!");
        for (int posDegrees = 0; posDegrees <= 180; posDegrees++) {
        servo1.write(posDegrees);
        // Serial.println(posDegrees);
        delay(15);
        DHT.read(DHT11_PIN);
        lcd.print("temp:");
        lcd.print(DHT.temperature);
        lcd.setCursor(0, 1);
        lcd.print("  humi:");
        lcd.println(DHT.humidity);
        delay(1000);
        }
        } else {
        lcd.print("The password is incorrect, ACCESS DENIED!");
        }
        input_password = "";  // clear input password
        } else {
        input_password += key;  // append new character to input password string
        }
        }
        }
        } else if (alarmArmed == false) {

        lcd.print("temp:");
        lcd.print(DHT.temperature);
        lcd.setCursor(0, 1);
        lcd.print("  humi:");
        lcd.println(DHT.humidity);
        delay(1000);
        }
        //--------------------------------------------
        // set the cursor to column 0, line 1, LCD
        // (note: line 1 is the second row, since counting begins with 0):

        // print the number of seconds since reset:
        // lcd.print(millis() / 1000);

        delay(100);
        //keypad code
        //----------------------------------
  /*char key = keypad.getKey();

 if (key) {
   Serial.println(key);

   if (key == '*') {
      input_password = ""; // clear input password
    } else if (key == '#') {
      if (password == input_password) {
        lcd.print("The password is correct, ACCESS GRANTED!");
        // DO YOUR WORK HERE

      } else {
        lcd.print("The password is incorrect, ACCESS DENIED!");
      }

      input_password = ""; // clear input password
    } else {
      input_password += key; // append new character to input password string
    }
  }
  */
        //-------------------------------------------------------------
  /*DHT.read(DHT11_PIN);
  lcd.print("temp:");
  lcd.print(DHT.temperature);
   lcd.setCursor(2, 0);
  lcd.print("  humi:");
  lcd.println(DHT.humidity);
  delay(1000);       //dht11 code*/



  /*
   for (int posDegrees = 0; posDegrees <= 180; posDegrees++) {
    servo1.write(posDegrees);
   // Serial.println(posDegrees);
    delay(15);
  }
   for (int posDegrees = 180; posDegrees >= 0; posDegrees--) {
    servo1.write(posDegrees);
    //Serial.println(posDegrees);
    delay(15);
  }     //servo motor code*/








        }
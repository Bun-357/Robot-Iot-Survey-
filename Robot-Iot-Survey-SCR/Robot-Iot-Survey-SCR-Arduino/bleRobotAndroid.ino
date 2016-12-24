
#include <SoftwareSerial.h>

SoftwareSerial mySerial(4, 5); // RX, TX

String data = "";

int rf = 6;
int rb = 9;
int lf = 10;
int lb = 11;

int aInPin = A0;

boolean startCar = false;

String tempsS = "xxxx";


void controlMotor();

void setup() {
  // put your setup code here, to run once:
  pinMode(rf, OUTPUT);
  pinMode(rb, OUTPUT);
  pinMode(lf, OUTPUT);
  pinMode(lb, OUTPUT);

  analogWrite(rf, 0);
  analogWrite(rb, 0);
  analogWrite(lf, 0);
  analogWrite(lb, 0);

  Serial.begin(115200);//for debug
  Serial.println("hi");

  // set the data rate for the SoftwareSerial port
  //for connect bluetooth
  mySerial.begin(115200);



}

void loop() {
  // put your main code here, to run repeatedly:

  if (mySerial.available()) {
    //Serial.write(mySerial.read());
    char s = (char)mySerial.read();
    data += s;
    if (s == '\n') {
      //complet data
      char fo = data.charAt(0);
      if (fo == 'x') {
        Serial.println("respond x");
        mySerial.println("Led8x8Mono.fnJ7P3gQdH6jNxU.M1JxfsMoyUHtpENvcBA7KXF5o.sv_mark_I");

      }

      if (fo == 'y') {
        startCar = true;
      }
      Serial.println(data);
      //
      if (startCar) {
        int v = analogRead(aInPin);
        mySerial.println(v);
        controlMotor();
      }
      data = "";
    }
  }

  //for debug
  if (Serial.available()) {
    mySerial.write(Serial.read());
  }
  //


}

void controlMotor() {

  char fo = data.charAt(0);
  char ba = data.charAt(2);
  char le = data.charAt(1);
  char ri = data.charAt(3);

  if ((fo == 'w') && (ba != 's') && (le != 'a') && (ri != 'd')) {
    //forward
    //analogWrite(rf, 255);//R mptor
    //analogWrite(ll,255);/L motor
    analogWrite(rf, 160);
    analogWrite(rb, 0);
    analogWrite(lf, 160);
    analogWrite(lb, 0);

    Serial.println("F");
  }

  if ((fo != 'w') && (ba == 's') && (le != 'a') && (ri != 'd')) {
    //back
    analogWrite(rf, 0);
    analogWrite(rb, 128);
    analogWrite(lf, 0);
    analogWrite(lb, 128);
    Serial.println("B");
  }



  if ((fo == 'w') && (ba != 's') && (le == 'a') && (ri != 'd') || (fo != 'w') && (ba != 's') && (le == 'a') && (ri != 'd')) {
    //left
    //analogWrite(rr, 255);
    //analogWrite(ll, 0);

    analogWrite(rf, 180);
    analogWrite(rb, 0);
    analogWrite(lf, 0);
    analogWrite(lb, 0);
    Serial.println("L");
  }

  if ((fo != 'w') && (ba != 's') && (le == 'a') && (ri != 'd')) {
    //spin L
    analogWrite(rf, 120);
    analogWrite(rb, 0);
    analogWrite(lf, 0);
    analogWrite(lb, 120);

  }


  if ((fo == 'w') && (ba != 's') && (le != 'a') && (ri == 'd')) {
    //right
    analogWrite(rf, 0);
    analogWrite(rb, 0);
    analogWrite(lf, 180);
    analogWrite(lb, 0);
    Serial.println("R");
  }

  if ((fo != 'w') && (ba != 's') && (le != 'a') && (ri == 'd')) {
    //spin R
    analogWrite(rf, 0);
    analogWrite(rb, 120);
    analogWrite(lf, 120);
    analogWrite(lb, 0);

  }

  if ((fo != 'w') && (ba == 's') && (le != 'a') && (ri == 'd')) {

    analogWrite(rf, 0);
    analogWrite(rb, 0);
    analogWrite(lf, 0);
    analogWrite(lb, 180);
    Serial.println("BR");
  }

  if ((fo != 'w') && (ba == 's') && (le == 'a') && (ri != 'd')) {

    analogWrite(rf, 0);
    analogWrite(rb, 180);
    analogWrite(lf, 0);
    analogWrite(lb, 0);
    Serial.println("BL");
  }

  if ((fo != 'w') && (ba != 's') && (le != 'a') && (ri != 'd')) {
    //right
    analogWrite(rf, 0);
    analogWrite(rb, 0);
    analogWrite(lf, 0);
    analogWrite(lb, 0);
    Serial.println("stop");
  }


}


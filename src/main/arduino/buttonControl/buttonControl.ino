
const int  buttonPin1 = 12;
const int  buttonPin2 = 10;
const int  buttonPin3 = 8;
const int ledPin1 = 11;
const int ledPin2 = 9;
const int ledPin3 = 7;
int state1 = LOW;
int state2 = LOW;
int state3 = LOW;
int lastButtonState1 = 0;
int lastButtonState2 = 0;
int lastButtonState3 = 0;

void setup() {
  pinMode(buttonPin1, INPUT);
  pinMode(ledPin1, OUTPUT);
  pinMode(buttonPin2, INPUT);
  pinMode(ledPin2, OUTPUT);
  pinMode(buttonPin3, INPUT);
  pinMode(ledPin3, OUTPUT);
  Serial.begin(9600);
}


void loop() {

  checkButton1();
  checkButton2();
  checkButton3();

}

void checkButton1() {
  int buttonState = digitalRead(buttonPin1);

  if (buttonState != lastButtonState1) {
    if (buttonState == HIGH) {
      state1 = !state1;
      printToSerial("wc1-1", state1);
      digitalWrite(ledPin1, state1);
    }
    delay(50);
  }
  lastButtonState1 = buttonState;
}

void checkButton2() {
  int buttonState = digitalRead(buttonPin2);

  if (buttonState != lastButtonState2) {
    if (buttonState == HIGH) {
      state2 = !state2;
      printToSerial("wc1-5", state2);
      digitalWrite(ledPin2, state2);
    }
    delay(50);
  }
  lastButtonState2 = buttonState;
}

void checkButton3() {
  int buttonState = digitalRead(buttonPin3);

  if (buttonState != lastButtonState3) {
    if (buttonState == HIGH) {
      state3 = !state3;
      printToSerial("wc1-9", state3);
      digitalWrite(ledPin3, state3);
    }
    delay(50);
  }
  lastButtonState3 = buttonState;
}

void printToSerial(String id, int state) {
  String stateStr = state ? "opened" : "closed";
  String request = id + ":" + stateStr;
  Serial.println(request);
}









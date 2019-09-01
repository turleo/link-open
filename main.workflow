 name: Android CI

on: [push]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v1
    - name: set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        java-version: 1.8
    - name: Build with Gradle
      run: ./gradlew build
  
  notify:
    uses = "appleboy/telegram-action@master"
    secrets = [
      "968517413:AAGSRwXAQJvh0z5UXsS6r1oECMqsK_ocJ_k",
      "bm835_updates",
    ]
  
# 🏪 W 편의점 결제 시스템 구현 프로젝트

---

## 📋 프로젝트 개요
W 편의점 결제 시스템은 사용자가 상품을 선택하고, 프로모션 및 멤버십 할인 혜택을 적용하여 최종 결제 금액을 계산하는 프로그램입니다. 

결제 후 영수증을 출력하며, 추가 구매 여부를 확인합니다.

---

## 📝 구현 기능 목록

### ⚙️ 재고 관리 기능
- **설명**: 초기 재고 정보를 `enum`으로 받아 각 재고 객체를 생성하고 관리합니다. 상품별 재고를 차감하며, 최신 재고 상태를 유지합니다.
- **요구사항**: 각 상품의 재고 상태를 실시간으로 업데이트하여, 다음 고객이 구매할 때 정확한 정보를 제공합니다.

### ⚙️ 재고 출력 기능
- **설명**: 현재 보유 중인 상품과 재고 정보를 출력합니다. 재고가 없는 경우 “재고 없음”을 표시합니다.
- **요구사항**: 상품명, 가격, 프로모션 여부, 재고 수량을 보기 좋게 정렬하여 출력합니다.

### ⚙️ 사용자 입력 기능
- **설명**: 구매할 상품명과 수량을 입력받습니다. 입력 형식은 `[상품명-수량]`이며, 각 항목은 쉼표로 구분됩니다.
- **예외 처리**:
    - 잘못된 형식으로 입력 시 예외 발생
    - 존재하지 않는 상품 입력 시 예외 발생
    - 재고 수량을 초과한 경우 예외 발생
    - 예외 발생 시 "[ERROR]"로 시작하는 에러 메시지를 출력하고 다시 입력 받습니다.

### ⚙️ 구매 검증 및 진행 기능
- **설명**: 입력된 상품명과 수량이 올바른지 검증하여 구매를 진행합니다. 재고 수량을 차감하며, 필요한 경우 예외 처리합니다.

### ⚙️ 프로모션 적용 기능
- **설명**: 특정 상품에 대해 프로모션 혜택을 적용합니다. N+1 형태의 프로모션을 제공하며, 적용 여부를 사용자에게 묻습니다.
- **요구사항**: 프로모션 혜택을 받기 위한 추가 수량 안내 및 부족 시 일반 재고를 사용하도록 처리합니다.

### ⚙️ 멤버십 할인 기능
- **설명**: 멤버십 회원에게 프로모션 미적용 금액의 30%를 할인합니다. 할인 금액은 최대 8,000원으로 제한됩니다.
- **요구사항**: 멤버십 할인 적용 여부를 묻고, 조건에 맞게 할인 금액을 계산합니다.

### ⚙️ 최종 결제 금액 계산 기능
- **설명**: 구매한 상품의 총 금액에 프로모션 및 멤버십 할인을 적용하여 최종 결제 금액을 계산합니다.
- **요구사항**: 할인 금액과 최종 결제 금액을 정확히 계산하여 출력합니다. 각 할인은 프로모션 할인 후, 멤버십 할인을 순차적으로 적용합니다.

### ⚙️ 영수증 출력 기능
- **설명**: 구매한 상품 내역, 할인 적용 사항, 최종 결제 금액을 요약하여 영수증을 출력합니다.
- **요구사항**: 영수증 항목을 정렬하여 보기 쉽게 출력하며, 상품명, 수량, 금액을 포함합니다. 할인 내역과 최종 결제 금액을 강조합니다.

### ⚙️ 추가 구매 여부 입력 기능
- **설명**: 추가 구매 여부를 묻고, 선택에 따라 재고가 업데이트된 상품 목록을 보여줍니다.
- **요구사항**: 사용자가 추가 구매를 원할 경우, 다시 상품과 수량을 입력받아 결제 과정을 반복합니다.

---

## 💻 프로그래밍 요구 사항

- **JDK 21**을 사용하여 프로그램을 구현하며, JDK 21에서 문제없이 실행 가능해야 합니다.
- **외부 라이브러리 사용 제한**: `camp.nextstep.edu.missionutils`의 `DateTimes` 및 `Console` API를 사용합니다.
- **입출력 담당 클래스**:
    - **InputView**: 사용자로부터 상품명과 수량, 할인 적용 여부 등을 입력받는 역할을 합니다.
    - **OutputView**: 현재 재고 상태와 영수증을 출력하는 역할을 합니다.
- **코딩 규칙**:
    - 들여쓰기는 최대 2단계까지만 허용합니다.
    - 3항 연산자를 사용하지 않습니다.
    - 메서드는 최대 10줄 이내로 작성하며, 하나의 역할만 수행해야 합니다.
    - `else` 예약어 및 `switch/case` 구문을 사용하지 않습니다.
    - Java Enum을 활용하여 프로그램을 구현해야 합니다.
    - 프로그램 종료 시 `System.exit()`를 호출하지 않습니다.

---

## 🚨 예외 처리 요구 사항

- **명확한 예외 처리**: `IllegalArgumentException` 및 `IllegalStateException`을 사용하여 명확한 예외 상황을 처리합니다.
- **에러 메시지**: 모든 오류 메시지는 "[ERROR]"로 시작하며, 상황에 맞는 안내 메시지를 출력합니다.
- **입력 재시도**: 예외가 발생한 경우, 해당 입력부터 다시 입력을 받습니다.

---

## 🧪 테스트 요구 사항

- **단위 테스트**: `JUnit 5`와 `AssertJ`를 활용하여 각 기능이 예상대로 동작하는지 검증하는 단위 테스트를 작성합니다.
- **테스트 대상 제외**: UI 로직과 같은 입출력 관련 부분은 테스트 대상에서 제외합니다.

---

## 💡 실행 예시
```
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 사이다 1,000원 8개 탄산2+1
- 오렌지주스 1,800원 9개 MD추천상품
- 탄산수 1,200원 5개 탄산2+1
- 물 500원 10개
- 감자칩 1,500원 5개 반짝할인
- 초코바 1,200원 5개 MD추천상품
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-3],[에너지바-5]

멤버십 할인을 받으시겠습니까? (Y/N)
Y

==============W 편의점================
상품명      수량      금액
콜라       3        3,000
에너지바    5       10,000
=============증정====================
콜라        1
====================================
총구매액      13,000
행사할인       -1,000
멤버십할인     -3,000
내실돈        9,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
Y

안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 7개 탄산2+1
- 사이다 1,000원 8개 탄산2+1
- 오렌지주스 1,800원 9개 MD추천상품
- 탄산수 1,200원 5개 탄산2+1
- 물 500원 10개
- 감자칩 1,500원 5개 반짝할인
- 초코바 1,200원 5개 MD추천상품
- 에너지바 2,000원 재고 없음
- 정식도시락 6,400원 8개
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-10]

현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
Y

멤버십 할인을 받으시겠습니까? (Y/N)
N

==============W 편의점================
상품명      수량      금액
콜라       10       10,000
=============증정====================
콜라        2
====================================
총구매액      10,000
행사할인       -2,000
멤버십할인     -0
내실돈        8,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
N
```
---
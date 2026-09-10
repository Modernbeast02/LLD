# ATM Machine - Low Level Design

## 1. Overview

This project implements an ATM Machine using Low-Level Design principles and commonly used design patterns.

The ATM supports:

- Card insertion
- PIN authentication
- Balance inquiry
- Cash withdrawal
- Cash deposit
- Card ejection

The design focuses on:

- Clean separation of responsibilities
- Extensibility
- State-dependent ATM behavior
- Transaction creation
- Cash dispensing
- Basic concurrency handling

---

## 2. Design Patterns Used

| Pattern | Used In | Purpose |
|---|---|---|
| **State Pattern** | `state` package | ATM behaves differently based on its current state |
| **Factory Pattern** | `TransactionFactory` | Creates the appropriate transaction |
| **Chain of Responsibility** | `cash` package | Distributes cash denomination by denomination |

### Pattern summary

```text
State Pattern
    ↓
Controls what the ATM can do in each state.

Factory Pattern
    ↓
Creates the correct Transaction object.

Chain of Responsibility
    ↓
Passes the remaining withdrawal amount through
₹500 → ₹200 → ₹100 handlers.
```

---

# 3. Project Structure

```text
com.ankur.lld
└── atm
    │
    ├── bank
    │   ├── BankService.java
    │   └── InMemoryBankService.java
    │
    ├── cash
    │   ├── CashHandler.java
    │   ├── CashInventory.java
    │   ├── FiveHundredHandler.java
    │   ├── OneHundredHandler.java
    │   └── TwoHundredHandler.java
    │
    ├── enums
    │   └── TransactionType.java
    │
    ├── factory
    │   └── TransactionFactory.java
    │
    ├── model
    │   ├── Account.java
    │   └── Card.java
    │
    ├── state
    │   ├── ATM.java
    │   ├── ATMState.java
    │   ├── AuthenticatedState.java
    │   ├── CardInsertedState.java
    │   └── IdleState.java
    │
    ├── transaction
    │   ├── BalanceInquiryTransaction.java
    │   ├── DepositTransaction.java
    │   ├── Transaction.java
    │   └── WithdrawalTransaction.java
    │
    └── Main.java
```

---

# 4. High-Level Architecture

```text
                         Main
                          |
                          v
                         ATM
                          |
                    State Pattern
                          |
          +---------------+---------------+
          |               |               |
          v               v               v
       IdleState    CardInsertedState  AuthenticatedState
                                          |
                                          |
                                  TransactionFactory
                                          |
                         +----------------+----------------+
                         |                |                |
                         v                v                v
                   Withdrawal         Deposit          BalanceInquiry
                   Transaction       Transaction        Transaction
                         |                |                |
                         |                |                |
                         v                v                v
                   CashInventory    BankService       BankService
                         |                |                |
                         v                v                v
                   CashHandler      InMemoryBankService
                     Chain                  |
                         |                  v
                         |               Account
                         |
                  +------+------+------+
                  |      |      |
                 ₹500   ₹200   ₹100
```

---

# 5. Core Components

The system can be understood using five major parts:

```text
1. ATM + States
2. Transactions
3. Transaction Factory
4. Bank Service + Account
5. Cash Inventory + Cash Handler Chain
```

---

# 6. ATM

File:

```text
state/ATM.java
```

`ATM` is the **Context** in the State Pattern.

It maintains information such as:

```text
currentState
card
bankService
cashInventory
```

The ATM does not itself decide what should happen for every operation.

Instead, it delegates operations to its current state.

For example:

```text
ATM.withdraw(amount)
        |
        v
currentState.withdraw(amount)
```

This means the current state decides whether withdrawal is allowed.

---

# 7. ATMState

File:

```text
state/ATMState.java
```

This is the common interface implemented by all ATM states.

Typical operations are:

```text
insertCard()
enterPin()
checkBalance()
withdraw()
deposit()
ejectCard()
```

Every state implements this interface.

---

# 8. ATM States

There are three states:

```text
IDLE
CARD_INSERTED
AUTHENTICATED
```

The state flow is:

```text
                insertCard()
        +------------------------>
        |
        |
   +---------+              +------------------+
   |  IDLE   |              | CARD_INSERTED   |
   +---------+              +------------------+
        ^                            |
        |                            |
        | eject                      | correct PIN
        |                            |
        |                            v
        |                    +------------------+
        +--------------------| AUTHENTICATED    |
             eject           +------------------+
```

---

# 9. IdleState

File:

```text
state/IdleState.java
```

This is the initial state of the ATM.

### Allowed operations

```text
insertCard()
```

### Not allowed

```text
enterPin()
checkBalance()
withdraw()
deposit()
```

When a card is inserted:

```text
ATM
 |
 | insertCard(card)
 v
IdleState
 |
 | store card
 |
 | change state
 v
CardInsertedState
```

---

# 10. CardInsertedState

File:

```text
state/CardInsertedState.java
```

This state means:

> A card has been inserted, but the user has not authenticated yet.

### Allowed

```text
enterPin()
ejectCard()
```

### Not allowed

```text
checkBalance()
withdraw()
deposit()
```

PIN authentication flow:

```text
ATM
 |
 v
CardInsertedState
 |
 | enterPin(pin)
 v
BankService
 |
 v
authenticate(card, pin)
 |
 +----------------------+
 |                      |
 | wrong PIN            | correct PIN
 |                      |
 v                      v
remain in state     AuthenticatedState
```

---

# 11. AuthenticatedState

File:

```text
state/AuthenticatedState.java
```

This state means:

> The card has been inserted and the PIN has been successfully verified.

The user can now perform:

```text
checkBalance()
withdraw()
deposit()
ejectCard()
```

For transactions, `AuthenticatedState` uses `TransactionFactory`.

Example:

```text
AuthenticatedState
        |
        | withdraw(1300)
        v
TransactionFactory
        |
        v
WithdrawalTransaction
        |
        v
execute()
```

---

# 12. Why State Pattern?

Without State Pattern, `ATM` could contain a large amount of conditional logic:

```text
if state == IDLE
    ...

if state == CARD_INSERTED
    ...

if state == AUTHENTICATED
    ...
```

As the number of states increases, this becomes harder to maintain.

With State Pattern:

```text
ATM
 |
 v
currentState
 |
 +---- IdleState
 |
 +---- CardInsertedState
 |
 +---- AuthenticatedState
```

Each state owns its own behavior.

The ATM remains the context that delegates operations.

---

# 13. Transaction Layer

The transaction package contains:

```text
Transaction
WithdrawalTransaction
DepositTransaction
BalanceInquiryTransaction
```

Relationship:

```text
Transaction
     |
     +---- WithdrawalTransaction
     |
     +---- DepositTransaction
     |
     +---- BalanceInquiryTransaction
```

---

# 14. Transaction Interface

File:

```text
transaction/Transaction.java
```

The common operation is:

```text
execute()
```

Every transaction implements this operation.

This allows the caller to work with:

```text
Transaction
```

instead of depending on a concrete transaction type.

---

# 15. TransactionType

File:

```text
enums/TransactionType.java
```

The enum represents supported transaction types:

```text
WITHDRAW
DEPOSIT
BALANCE_INQUIRY
```

Instead of passing arbitrary strings:

```text
"withdraw"
"deposit"
"balance"
```

we use:

```text
TransactionType.WITHDRAW
TransactionType.DEPOSIT
TransactionType.BALANCE_INQUIRY
```

This provides type safety and avoids spelling/casing mistakes.

---

# 16. TransactionFactory

File:

```text
factory/TransactionFactory.java
```

The factory is responsible for creating the correct transaction object.

```text
                     TransactionFactory
                            |
              +-------------+-------------+
              |             |             |
              v             v             v
          WITHDRAW       DEPOSIT      BALANCE_INQUIRY
              |             |             |
              v             v             v
        Withdrawal      Deposit       BalanceInquiry
        Transaction     Transaction    Transaction
```

Example:

```text
TransactionType.WITHDRAW
          |
          v
WithdrawalTransaction
```

The caller does not need to know how the transaction object is constructed.

---

# 17. Why Transaction Factory?

Without a factory, the state would directly create concrete classes:

```text
AuthenticatedState
       |
       +--> new WithdrawalTransaction(...)
       |
       +--> new DepositTransaction(...)
       |
       +--> new BalanceInquiryTransaction(...)
```

With the factory:

```text
AuthenticatedState
       |
       v
TransactionFactory
       |
       +--> WithdrawalTransaction
       +--> DepositTransaction
       +--> BalanceInquiryTransaction
```

Transaction creation is centralized.

---

# 18. WithdrawalTransaction

File:

```text
transaction/WithdrawalTransaction.java
```

Responsible for withdrawal.

It interacts with:

```text
CashInventory
BankService
```

High-level flow:

```text
WithdrawalTransaction
        |
        +-----------> CashInventory
        |
        +-----------> BankService
```

The cash inventory handles the ATM's available physical notes.

The bank service handles the user's account.

---

# 19. DepositTransaction

File:

```text
transaction/DepositTransaction.java
```

Responsible for depositing money.

Flow:

```text
DepositTransaction
        |
        v
BankService
        |
        v
Account
```

---

# 20. BalanceInquiryTransaction

File:

```text
transaction/BalanceInquiryTransaction.java
```

Responsible for checking the user's balance.

Flow:

```text
BalanceInquiryTransaction
        |
        v
BankService
        |
        v
Account
```

---

# 21. Bank Layer

The bank package contains:

```text
BankService
InMemoryBankService
```

---

# 22. BankService

File:

```text
bank/BankService.java
```

This is an abstraction between the ATM and the banking implementation.

The ATM depends on:

```text
BankService
```

rather than directly depending on:

```text
InMemoryBankService
```

Architecture:

```text
ATM
 |
 v
BankService interface
 |
 v
InMemoryBankService
```

In a real application, the implementation could be replaced with:

```text
RealBankService
RemoteBankService
BankApiService
```

without changing the ATM's core state logic.

---

# 23. InMemoryBankService

File:

```text
bank/InMemoryBankService.java
```

This is the concrete bank implementation used for this project.

It provides operations such as:

```text
authenticate()
getBalance()
withdraw()
deposit()
```

It communicates with:

```text
Card
Account
```

Authentication:

```text
Card
 |
 v
InMemoryBankService
 |
 | verify PIN
 v
success / failure
```

Account operation:

```text
InMemoryBankService
 |
 v
Account
 |
 +---- withdraw()
 +---- deposit()
 +---- getBalance()
```

---

# 24. Model Layer

The model package contains:

```text
Account
Card
```

---

# 25. Card

File:

```text
model/Card.java
```

Represents the ATM card.

It contains card-related information such as:

```text
cardNumber
```

The card is used during:

```text
Authentication
Withdrawal
Deposit
Balance Inquiry
```

---

# 26. Account

File:

```text
model/Account.java
```

Represents the user's bank account.

It maintains:

```text
accountNumber
balance
```

It provides operations such as:

```text
getBalance()
withdraw()
deposit()
```

Account balance modifications should be thread-safe.

---

# 27. Cash Layer

The cash package contains:

```text
CashInventory
CashHandler
FiveHundredHandler
TwoHundredHandler
OneHundredHandler
```

This layer is responsible for distributing the ATM's physical cash.

---

# 28. CashInventory

File:

```text
cash/CashInventory.java
```

`CashInventory` manages the ATM's available notes.

Example:

```text
₹500 -> 10 notes
₹200 -> 10 notes
₹100 -> 20 notes
```

The denomination chain is:

```text
FiveHundredHandler
        |
        v
TwoHundredHandler
        |
        v
OneHundredHandler
```

The rest of the application only needs to call:

```text
cashInventory.dispense(amount)
```

It does not need to know how denominations are selected.

---

# 29. CashHandler

File:

```text
cash/CashHandler.java
```

This is the base handler for cash denominations.

It contains the common logic for:

- Checking how many notes are needed
- Checking available notes
- Taking as many notes as possible
- Calculating the remaining amount
- Passing the remaining amount to the next handler

Conceptually:

```text
CashHandler
 |
 +-- denomination
 +-- availableNotes
 +-- next
 +-- dispense()
```

---

# 30. Concrete Cash Handlers

## FiveHundredHandler

```text
denomination = ₹500
```

## TwoHundredHandler

```text
denomination = ₹200
```

## OneHundredHandler

```text
denomination = ₹100
```

The common algorithm stays in `CashHandler`.

The concrete classes mainly specify their denomination.

---

# 31. Chain of Responsibility

The cash handlers form a chain:

```text
₹500 Handler
      |
      v
₹200 Handler
      |
      v
₹100 Handler
```

Each handler:

1. Takes as many notes as possible.
2. Calculates the remaining amount.
3. Passes the remaining amount to the next handler.

---

# 32. Example: Withdraw ₹1300

Suppose the ATM has:

```text
₹500
₹200
₹100
```

The request enters the ₹500 handler.

### Step 1 — ₹500 Handler

```text
1300 / 500 = 2
```

Give:

```text
2 × ₹500 = ₹1000
```

Remaining:

```text
₹300
```

Pass ₹300 to the ₹200 handler.

---

### Step 2 — ₹200 Handler

```text
300 / 200 = 1
```

Give:

```text
1 × ₹200 = ₹200
```

Remaining:

```text
₹100
```

Pass ₹100 to the ₹100 handler.

---

### Step 3 — ₹100 Handler

```text
100 / 100 = 1
```

Give:

```text
1 × ₹100 = ₹100
```

Remaining:

```text
₹0
```

Final result:

```text
2 × ₹500
1 × ₹200
1 × ₹100
```

Total:

```text
₹1300
```

---

# 33. Greedy Algorithm vs Chain of Responsibility

These are two different concepts.

### Greedy Algorithm

Determines:

> Take as many large denomination notes as possible.

### Chain of Responsibility

Determines:

> After one denomination handler finishes, pass the remaining amount to the next handler.

Therefore:

```text
Greedy
  =
Cash selection algorithm

Chain of Responsibility
  =
Object communication / design pattern
```

Both are used together in this design.

---

# 34. Important Cash Allocation Rule

The cash inventory should not partially modify itself while still determining whether the amount can be dispensed.

Bad flow:

```text
Take ₹500
Take ₹200
Take ₹100
Oops, remaining amount cannot be formed.
```

Now the inventory has already been modified.

Better flow:

### Phase 1 — Calculate

```text
₹500 -> 2 notes
₹200 -> 1 note
₹100 -> 1 note
```

Check:

```text
remainingAmount == 0
```

### Phase 2 — Commit

Only after the complete amount can be formed:

```text
remove 2 × ₹500
remove 1 × ₹200
remove 1 × ₹100
```

This avoids partial cash inventory updates.

---

# 35. Complete File Dependency Map

```text
Main
 |
 +--------------------+
 |        |           |
 v        v           v
ATM     Card       Account
 |                     ^
 |                     |
 |                     |
 +---- BankService ----+
 |
 +---- CashInventory
```

More specifically:

```text
Main
 |
 +--> Card
 +--> Account
 +--> InMemoryBankService
 +--> CashInventory
 +--> ATM
```

---

## ATM.java

Uses:

```text
ATMState
Card
BankService
CashInventory
```

---

## IdleState.java

Uses:

```text
ATM
Card
CardInsertedState
```

---

## CardInsertedState.java

Uses:

```text
ATM
BankService
AuthenticatedState
```

---

## AuthenticatedState.java

Uses:

```text
ATM
TransactionFactory
TransactionType
Transaction
```

---

## TransactionFactory.java

Creates:

```text
WithdrawalTransaction
DepositTransaction
BalanceInquiryTransaction
```

---

## WithdrawalTransaction.java

Uses:

```text
Card
BankService
CashInventory
```

---

## DepositTransaction.java

Uses:

```text
Card
BankService
```

---

## BalanceInquiryTransaction.java

Uses:

```text
Card
BankService
```

---

## InMemoryBankService.java

Uses:

```text
Card
Account
```

---

## CashInventory.java

Uses:

```text
CashHandler
FiveHundredHandler
TwoHundredHandler
OneHundredHandler
```

---

## CashHandler.java

Uses:

```text
next CashHandler
```

to create the chain.

---

# 36. Complete Class Relationship

```text
                         +----------------+
                         |      ATM       |
                         +-------+--------+
                                 |
                                 | has
                                 v
                         +----------------+
                         |   ATMState     |
                         |   <<interface>>|
                         +-------+--------+
                                 |
             +-------------------+-------------------+
             |                   |                   |
             v                   v                   v
        IdleState       CardInsertedState    AuthenticatedState
                                                     |
                                                     | uses
                                                     v
                                            TransactionFactory
                                                     |
                                                     v
                                                Transaction
                                                     |
                              +----------------------+------------------+
                              |                      |                  |
                              v                      v                  v
                       WithdrawalTransaction   DepositTransaction   BalanceInquiry
                              |                                      Transaction
                              |
                     +--------+---------+
                     |                  |
                     v                  v
                CashInventory       BankService
                     |                  |
                     v                  v
                 CashHandler      InMemoryBankService
                     |                  |
          +----------+----------+       v
          |          |          |    Account
          v          v          v
        ₹500       ₹200       ₹100
```

---

# 37. Complete Withdrawal Flow

Suppose the user wants to withdraw:

```text
₹1300
```

The call flow is:

```text
Main
 |
 v
ATM.withdraw(1300)
 |
 v
AuthenticatedState.withdraw(1300)
 |
 v
TransactionFactory.create(WITHDRAW)
 |
 v
WithdrawalTransaction
 |
 v
WithdrawalTransaction.execute()
 |
 +----------------------------+
 |                            |
 v                            v
CashInventory              BankService
 |                            |
 v                            v
CashHandler Chain         InMemoryBankService
 |                            |
 v                            v
500 -> 200 -> 100          Account
```

---

# 38. Complete ATM Session

The complete user journey is:

```text
                    USER
                     |
                     v
                Insert Card
                     |
                     v
                    ATM
                     |
                     v
                 IdleState
                     |
                     | insertCard()
                     v
            CardInsertedState
                     |
                     | enterPin()
                     v
                BankService
                     |
                     | authenticate()
                     v
               Authentication
                     |
                PIN correct
                     |
                     v
            AuthenticatedState
                     |
        +------------+-------------+
        |            |             |
        v            v             v
     Balance      Withdraw       Deposit
        |            |             |
        v            v             v
    TransactionFactory
        |            |             |
        v            v             v
    Balance       Withdrawal     Deposit
    Transaction   Transaction    Transaction
                     |
                +----+----+
                |         |
                v         v
          CashInventory  BankService
                |         |
                v         v
           Cash Chain   Account
                |
                v
          Cash Dispensed
                     |
                     v
                Eject Card
                     |
                     v
                  ATM
                     |
                     v
                 IdleState
```

---

# 39. Detailed State Transition Flow

## Initial State

```text
ATM
 |
 currentState = IdleState
```

---

## Card Insertion

```text
ATM.insertCard(card)
        |
        v
IdleState.insertCard(card)
        |
        +-- ATM.card = card
        |
        +-- ATM.state = CardInsertedState
```

---

## PIN Authentication

```text
ATM.enterPin(pin)
        |
        v
CardInsertedState.enterPin(pin)
        |
        v
BankService.authenticate(card, pin)
        |
        +---- false ----> remain CardInsertedState
        |
        +---- true -----> AuthenticatedState
```

---

## Transaction

```text
ATM.withdraw(amount)
        |
        v
AuthenticatedState.withdraw(amount)
        |
        v
TransactionFactory
        |
        v
WithdrawalTransaction
        |
        v
execute()
```

---

## Card Ejection

```text
ATM.ejectCard()
        |
        v
CurrentState.ejectCard()
        |
        +-- ATM.card = null
        |
        +-- ATM.state = IdleState
```

---

# 40. Balance Inquiry Flow

```text
User
 |
 v
ATM.checkBalance()
 |
 v
AuthenticatedState.checkBalance()
 |
 v
TransactionFactory
 |
 | TransactionType.BALANCE_INQUIRY
 v
BalanceInquiryTransaction
 |
 v
execute()
 |
 v
BankService.getBalance()
 |
 v
InMemoryBankService
 |
 v
Account.getBalance()
 |
 v
Balance
```

---

# 41. Deposit Flow

```text
User
 |
 v
ATM.deposit(amount)
 |
 v
AuthenticatedState.deposit(amount)
 |
 v
TransactionFactory
 |
 | TransactionType.DEPOSIT
 v
DepositTransaction
 |
 v
execute()
 |
 v
BankService.deposit()
 |
 v
InMemoryBankService
 |
 v
Account.deposit()
```

---

# 42. Withdrawal Flow

```text
User
 |
 v
ATM.withdraw(amount)
 |
 v
AuthenticatedState.withdraw(amount)
 |
 v
TransactionFactory
 |
 | TransactionType.WITHDRAW
 v
WithdrawalTransaction
 |
 v
execute()
 |
 +--------------------------+
 |                          |
 v                          v
CashInventory            BankService
 |                          |
 v                          v
CashHandler Chain       InMemoryBankService
 |                          |
 v                          v
₹500 → ₹200 → ₹100       Account
 |
 v
Cash allocated
```

---

# 43. Concurrency

There are two important shared resources:

1. Account balance
2. ATM cash inventory

---

## Account Concurrency

Multiple operations could try to modify the same account simultaneously.

For example:

```text
Thread 1                 Thread 2

withdraw ₹1000           withdraw ₹1000
     |                        |
     +-----------+------------+
                 |
              Account
                 |
            synchronized
                 |
        one operation at a time
```

Operations such as:

```text
withdraw()
deposit()
```

should be thread-safe.

---

## Cash Inventory Concurrency

Two withdrawal requests should not consume the same physical notes.

Therefore the complete cash dispensing operation should be protected.

```text
Thread 1
   |
   v
CashInventory.dispense()
   |
   | synchronized
   v
Calculate + Commit
```

At the same time:

```text
Thread 2
   |
   v
waits
```

This prevents two concurrent withdrawals from incorrectly consuming the same notes.

---

# 44. Important Production Consideration

Cash dispensing and bank withdrawal are two separate resources.

They cannot simply be treated as one ACID database transaction.

For example:

```text
Cash dispensed successfully
        |
        v
Bank withdrawal fails
```

The customer received cash but the account was not debited.

The reverse can also happen:

```text
Bank withdrawal succeeds
        |
        v
Cash dispenser fails
```

The customer was debited but did not receive cash.

A production ATM would need mechanisms such as:

- Transaction journal
- Reversal
- Idempotency
- Audit logs
- Reconciliation
- Hardware failure handling

---

# 45. Why We Don't Have ATMStateFactory

The project has only three states:

```text
IdleState
CardInsertedState
AuthenticatedState
```

Their transitions are straightforward:

```text
IdleState
    |
    v
CardInsertedState
    |
    v
AuthenticatedState
```

Therefore a separate:

```text
ATMStateFactory
```

is not necessary.

The State Pattern itself does **not** require a factory.

We use a factory where it provides more value:

```text
TransactionFactory
```

because there are multiple transaction types that need to be created.

---



# 46. Design Principles

## Single Responsibility Principle

Each major class has one primary responsibility.

```text
ATM
    → Maintains ATM context and delegates to state

ATMState
    → Defines state-specific ATM behavior

Transaction
    → Represents a transaction

TransactionFactory
    → Creates transactions

BankService
    → Defines banking operations

Account
    → Maintains account balance

CashInventory
    → Manages ATM cash

CashHandler
    → Handles one denomination
```

---

## Open/Closed Principle

The transaction system can be extended.

For example, if we add:

```text
MiniStatementTransaction
```

we can add a new transaction class and update the factory without changing existing transaction behavior.

Similarly, new ATM states can be introduced by implementing `ATMState`.

---

## Dependency Inversion

The ATM depends on:

```text
BankService
```

rather than directly depending on:

```text
InMemoryBankService
```

This makes the banking implementation replaceable.


---

# 47. Final Mental Model

Remember the entire design as four layers:

```text
                ATM
                 |
                 v
        +------------------+
        |   STATE LAYER    |
        |                  |
        | Idle             |
        | CardInserted     |
        | Authenticated    |
        +--------+---------+
                 |
                 v
        +------------------+
        | TRANSACTION      |
        | LAYER            |
        |                  |
        | Factory          |
        | Withdrawal       |
        | Deposit          |
        | Balance          |
        +--------+---------+
                 |
          +------+------+
          |             |
          v             v
   +------------+  +------------+
   | BANK       |  | CASH       |
   | SERVICE    |  | INVENTORY  |
   +------------+  +------+-----+
                           |
                           v
                    Chain of
                   Responsibility
                           |
                    +------+------+------+
                    |      |      |
                   ₹500   ₹200   ₹100
```

---

# 48. Complete End-to-End Flow

The easiest way to remember the entire implementation:

```text
1. Card inserted
       ↓
2. ATM moves IDLE → CARD_INSERTED
       ↓
3. PIN entered
       ↓
4. BankService authenticates the card
       ↓
5. ATM moves CARD_INSERTED → AUTHENTICATED
       ↓
6. User chooses a transaction
       ↓
7. AuthenticatedState calls TransactionFactory
       ↓
8. Factory creates the required Transaction
       ↓
9. Transaction.execute()
       ↓
10. BankService / CashInventory perform the operation
       ↓
11. Account / CashHandler Chain are updated
       ↓
12. User ejects the card
       ↓
13. ATM returns to IDLE
```

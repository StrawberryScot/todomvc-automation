# TodoMVC React Test Automation Suite

## Team Members
- Kathy Peacock
- Benjamin Loveday
- Chibu Ihesiene

## Test Coverage

### Tests Implemented
We have automated the following tests from our test plan:

**"Automate Now" Tests:**
- TEST 1 Can't add an item with an empty value
- TEST 2 Can add an item with a single character
- TEST 3 Can delete a single item
- TEST 6.1 Can modify existing todo item
- TEST 6.2 Can modify same todo multiple times
- TEST 6.3 Can modify completed todo
- TEST 7 Pressing Escape during item modification cancels modification
- TEST 8 Can add another todo item to list
- TEST 15 Can clear complete todo items when >0 completed todo items are listed

**"Automate Later" Tests:**
- TEST 4 Can add characters and symbols
- TEST 5 Can add emoji characters (with and without text + combo)
- TEST 11.1 Can filter incompleted items
- TEST 11.2 Can filter completed items
- TEST 11.3 Can filter by all items
- TEST 13 Can mark all todo items as complete
- TEST 14 Can mark all todo items as incomplete

## Expected Test Results

### ✅ Passing Tests: [31]
These tests validate working functionality in the TodoMVC application.

### ❌ Failing Tests: [11]
These tests are **expected to fail** due to known bugs identified during our exploratory testing:

#### Known Bug 1: Single Character Input (TEST 2)
- **Issue:** Cannot add a todo with only 1 character
- **Expected:** Should accept single character todos
- **Actual:** Nothing happens when entering single character and pressing Enter
- **Tests Affected** TEST 2

#### Known Bug 2: Escape Key Doesn't Cancel Edit (TEST 7)
- **Issue:** Pressing Escape during edit doesn't cancel the modification
- **Expected:** Escape should revert changes
- **Actual:** Nothing happens when Escape is pressed
- **Tests Affected:** 5 parameterized test scenarios for TEST 7

#### Known Bug 3: Special Character Encoding (TEST 4)
- **Issue:** Some special characters display as HTML entities
- **Expected:** Characters like ' " & < > should display normally
- **Actual:** Display as &#x27; &quot; &amp; &lt; &gt;
- **Eg.:** "Test with 'single' and "double" quotes" displays as "Test with &#x27;single&#x27; and &quot;double&quot; quotes"
- **Tests Affected** 4 parameterized test scenarios for TEST 4:
  - "Test/with\\slashes" ,
  - "Test with 'single' and \"double\" quotes"
  - "Test with @#$%^&*()"
  - "Test with <angle> brackets"
 
#### Known Bug 4: Emoji Encoding (TEST 5)
- **Issue:** Some emojis are not compatible with ChromeDriver
- **Expected:** Characters like ' " & < > should display normally
- **Actual:** Error Message: "unknown error: ChromeDriver only supports characters in the BMP"
- **Workaround** This only affects ChromeDriver automation. Manual testing successful. FirefoxDriver could be used.
- **Tests Affected** 1 parameterized test scenario for TEST 5:
  - "Bug found 🐛"

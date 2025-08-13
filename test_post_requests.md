# POST Request Testing Guide

## Issues Fixed

The following issues with null values in POST requests have been resolved:

### 1. **Entity Validation**
- Added `@NotBlank` and `@Column(nullable = false)` annotations to prevent null values
- Added validation constraints to all required fields

### 2. **Controller Layer Validation**
- Added `@Valid` annotation to validate incoming requests
- Added null checks and proper error responses
- Changed from `@ModelAttribute` to `@RequestBody` for JSON requests
- Added exception handling for validation errors

### 3. **DAO Layer Validation**
- Added comprehensive null checks
- Added field validation before database operations
- Added proper error handling for data integrity violations

## How to Test POST Requests

### Correct POST Request (JSON)
```bash
curl -X POST http://localhost:8080/insert \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalName": "City General Hospital",
    "hospitalAddress": "123 Main Street",
    "hospitalCity": "New York",
    "hospitalState": "NY"
  }'
```

### Correct POST Request (Form Data)
```bash
curl -X POST http://localhost:8080/insert \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "hospitalName=City General Hospital&hospitalAddress=123 Main Street&hospitalCity=New York&hospitalState=NY"
```

## Common Null Value Issues and Solutions

### ❌ **Problem: Missing Required Fields**
```json
{
  "hospitalName": "City General Hospital",
  "hospitalAddress": "123 Main Street"
  // Missing hospitalCity and hospitalState
}
```
**Response:** 400 Bad Request with validation errors

### ❌ **Problem: Empty Strings**
```json
{
  "hospitalName": "",
  "hospitalAddress": "123 Main Street",
  "hospitalCity": "New York",
  "hospitalState": "NY"
}
```
**Response:** 400 Bad Request - "Hospital name is required"

### ❌ **Problem: Null Values**
```json
{
  "hospitalName": null,
  "hospitalAddress": "123 Main Street",
  "hospitalCity": "New York",
  "hospitalState": "NY"
}
```
**Response:** 400 Bad Request - "Hospital name is required"

### ❌ **Problem: Wrong Content Type**
```bash
curl -X POST http://localhost:8080/insert \
  -d "hospitalName=City General Hospital"
```
**Response:** 400 Bad Request - "Hospital object cannot be null"

## Error Response Examples

### Validation Error Response
```json
{
  "hospitalName": "Hospital name is required",
  "hospitalAddress": "Hospital address is required",
  "hospitalCity": "Hospital city is required",
  "hospitalState": "Hospital state is required"
}
```

### Null Object Error Response
```json
"Hospital object cannot be null"
```

### Database Error Response
```json
"Error saving hospital: Data integrity violation: not-null property references a null or transient value"
```

## Best Practices

1. **Always send all required fields** in POST requests
2. **Use proper Content-Type headers** (application/json for JSON, application/x-www-form-urlencoded for form data)
3. **Validate data on client side** before sending requests
4. **Handle error responses** appropriately in your client application
5. **Use meaningful error messages** to help debug issues

## Testing with Different Tools

### Using Postman
1. Set method to POST
2. Set URL to `http://localhost:8080/insert`
3. Set Content-Type header to `application/json`
4. Add request body with all required fields

### Using JavaScript/Fetch
```javascript
fetch('http://localhost:8080/insert', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    hospitalName: 'City General Hospital',
    hospitalAddress: '123 Main Street',
    hospitalCity: 'New York',
    hospitalState: 'NY'
  })
})
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.error('Error:', error));
``` 
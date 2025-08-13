# Postman Collection Setup Guide

## Overview
This guide explains how to import and use the comprehensive Postman collection for testing the Hospital Management System API, including all CRUD operations and validation scenarios.

## Files Included
- `Hospital_Management_System.postman_collection.json` - Complete Postman collection
- `POSTMAN_SETUP_GUIDE.md` - This setup guide

## How to Import the Collection

### Method 1: Import via Postman App
1. **Open Postman** application
2. **Click "Import"** button (top left)
3. **Choose Files** and select `Hospital_Management_System.postman_collection.json`
4. **Click "Import"** to add the collection

### Method 2: Import via Postman Web
1. **Go to** [postman.com](https://postman.com) and sign in
2. **Click "Import"** button
3. **Upload** the `Hospital_Management_System.postman_collection.json` file
4. **Click "Import"** to add the collection

## Collection Structure

The collection is organized into the following folders:

### 📁 GET Operations
- **Get All Hospitals** - Retrieve all hospitals
- **Get Hospital by ID** - Get specific hospital by ID
- **Get Hospital by ID - Invalid ID** - Test error handling

### 📁 POST Operations
- **Create Hospital - Valid Data** - ✅ Success scenario
- **Create Hospital - Missing Required Fields** - ❌ Validation error
- **Create Hospital - Empty Strings** - ❌ Validation error
- **Create Hospital - Null Values** - ❌ Validation error
- **Create Hospital - Wrong Content Type** - ❌ Content type error
- **Create Hospital - Form Data (Valid)** - ✅ Form data success

### 📁 PUT Operations
- **Update Hospital - Valid Data** - ✅ Success scenario
- **Update Hospital - Missing ID** - ❌ Missing ID error
- **Update Hospital - Non-existent ID** - ❌ Not found error
- **Update Hospital - Invalid Data** - ❌ Validation error

### 📁 DELETE Operations
- **Delete Hospital by ID** - ✅ Success scenario
- **Delete Hospital - Invalid ID** - ❌ Not found error
- **Delete Hospital - Null ID** - ❌ Missing ID error

### 📁 Test Scenarios
- **Complete CRUD Flow Test** - End-to-end testing
- **Bulk Create Hospitals** - Create test data
- **Create Another Hospital** - Additional test data

## Environment Setup

### 1. Set Base URL Variable
The collection uses a variable `{{base_url}}` which defaults to `http://localhost:8080`.

**To change the base URL:**
1. **Right-click** on the collection name
2. **Select "Edit"**
3. **Go to "Variables" tab**
4. **Update** the `base_url` value to your server URL

### 2. Environment Variables (Optional)
You can create environment variables for different environments:

```json
{
  "development": {
    "base_url": "http://localhost:8080"
  },
  "staging": {
    "base_url": "http://staging-server:8080"
  },
  "production": {
    "base_url": "http://production-server:8080"
  }
}
```

## How to Test

### Step 1: Start Your Application
```bash
# Navigate to your project directory
cd /path/to/your/demo

# Run the Spring Boot application
./gradlew bootRun
```

### Step 2: Test Basic Operations
1. **Run "Get All Hospitals"** - Should return empty array initially
2. **Run "Create Hospital - Valid Data"** - Should create a hospital
3. **Run "Get All Hospitals"** again - Should show the created hospital
4. **Run "Get Hospital by ID"** - Should return the specific hospital

### Step 3: Test Validation Scenarios
1. **Run "Create Hospital - Missing Required Fields"** - Should return 400 error
2. **Run "Create Hospital - Empty Strings"** - Should return 400 error
3. **Run "Create Hospital - Null Values"** - Should return 400 error

### Step 4: Test Update Operations
1. **Run "Update Hospital - Valid Data"** - Should update successfully
2. **Run "Update Hospital - Missing ID"** - Should return 400 error
3. **Run "Update Hospital - Non-existent ID"** - Should return 400 error

### Step 5: Test Delete Operations
1. **Run "Delete Hospital by ID"** - Should delete successfully
2. **Run "Delete Hospital - Invalid ID"** - Should return 400 error

## Expected Responses

### ✅ Successful Responses

**Create Hospital (201 Created):**
```json
{
  "id": 1,
  "hospitalName": "City General Hospital",
  "hospitalAddress": "123 Main Street",
  "hospitalCity": "New York",
  "hospitalState": "NY"
}
```

**Get All Hospitals (200 OK):**
```json
[
  {
    "id": 1,
    "hospitalName": "City General Hospital",
    "hospitalAddress": "123 Main Street",
    "hospitalCity": "New York",
    "hospitalState": "NY"
  }
]
```

### ❌ Error Responses

**Validation Error (400 Bad Request):**
```json
{
  "hospitalName": "Hospital name is required",
  "hospitalAddress": "Hospital address is required",
  "hospitalCity": "Hospital city is required",
  "hospitalState": "Hospital state is required"
}
```

**Null Object Error (400 Bad Request):**
```json
"Hospital object cannot be null"
```

**Not Found Error (404 Not Found):**
```json
"Hospital with ID 999 not found"
```

## Automated Testing

The collection includes automated tests that run after each request:

### Test Scripts
- **Status Code Validation** - Ensures 200/201 for success
- **Response Time Check** - Ensures response time < 2000ms
- **Header Validation** - Ensures Content-Type header is present
- **Data Storage** - Stores created IDs for subsequent requests

### Running Tests
1. **Select a request** in the collection
2. **Click "Send"** to execute
3. **Check "Test Results"** tab for automated test results
4. **View "Console"** for detailed logs

## Troubleshooting

### Common Issues

**1. Connection Refused (Error 7)**
- Ensure your Spring Boot application is running
- Check if the port 8080 is available
- Verify the base URL in collection variables

**2. 404 Not Found**
- Check if your application endpoints match the collection URLs
- Verify the request path is correct
- Ensure the application is properly started

**3. 400 Bad Request**
- Check request body format (JSON vs Form Data)
- Verify all required fields are present
- Ensure Content-Type header is set correctly

**4. 500 Internal Server Error**
- Check application logs for detailed error messages
- Verify database connection
- Check if all dependencies are properly configured

### Debug Steps
1. **Check Application Logs** - Look for error messages in console
2. **Verify Database** - Ensure PostgreSQL is running and accessible
3. **Test Individual Endpoints** - Use curl or browser to test manually
4. **Check Dependencies** - Ensure all required dependencies are included

## Best Practices

### 1. Test Order
- Always test GET operations first to verify connectivity
- Create test data before testing UPDATE/DELETE operations
- Test validation scenarios after successful operations

### 2. Data Management
- Use the "Test Scenarios" folder to create test data
- Clean up test data after testing
- Use different data for different test scenarios

### 3. Environment Management
- Use different collections for different environments
- Set up environment variables for configuration
- Document environment-specific requirements

### 4. Error Handling
- Always test both success and failure scenarios
- Verify error messages are meaningful
- Test edge cases and boundary conditions

## Advanced Features

### 1. Collection Runner
- **Select multiple requests** to run in sequence
- **Set iteration count** for load testing
- **Configure delays** between requests

### 2. Newman (Command Line)
```bash
# Install Newman
npm install -g newman

# Run collection
newman run Hospital_Management_System.postman_collection.json

# Run with environment
newman run Hospital_Management_System.postman_collection.json -e environment.json
```

### 3. CI/CD Integration
- Export collection as JSON
- Use Newman in CI/CD pipelines
- Generate test reports automatically

## Support

If you encounter issues:
1. **Check the troubleshooting section** above
2. **Review application logs** for detailed error messages
3. **Verify all dependencies** are properly configured
4. **Test with curl** to isolate Postman-specific issues

## Additional Resources

- [Postman Documentation](https://learning.postman.com/)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)
- [REST API Testing Best Practices](https://restfulapi.net/testing-rest-api/) 
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;

class CustomerDataTest {

  private CustomerData customerData;

  @BeforeEach
  void setUp() {
    // Create a new CustomerData object for each test
    customerData = new CustomerData(new HashMap<>());
  }

  @Test
  void setAttribute() {
    // Test setting an attribute
    customerData.setAttribute("name", "John Doe");
    assertEquals("John Doe", customerData.getAttribute("name"));
  }

  @Test
  void getAttribute() {
    // Test getting an attribute
    customerData.setAttribute("age", "30");
    assertEquals("30", customerData.getAttribute("age"));
  }

  @Test
  void getAllAttributes() {
    // Test getting all attributes
    customerData.setAttribute("city", "New York");
    customerData.setAttribute("country", "USA");
    Iterator<String> iterator = customerData.getAllAttributes();
    assertTrue(iterator.hasNext());
    assertEquals("country", iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals("city", iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  void testEquals() {
    // Test equals method
    customerData.setAttribute("name", "John Doe");
    CustomerData otherCustomerData = new CustomerData(new HashMap<>());
    assertFalse(customerData.equals(otherCustomerData));
    otherCustomerData.setAttribute("name", "Jane Doe");

    assertFalse(customerData.equals(otherCustomerData));
    otherCustomerData.setAttribute("name", "John Doe");
    assertTrue(customerData.equals(otherCustomerData));
  }

  @Test
  void testHashCode() {
    // Test hashCode method
    CustomerData otherCustomerData = new CustomerData(new HashMap<>());
    otherCustomerData.setAttribute("name", "Jane Doe");
    assertNotEquals(customerData.hashCode(), otherCustomerData.hashCode());
    otherCustomerData.setAttribute("name", "John Doe");
    customerData.setAttribute("name", "John Doe");
    assertEquals(customerData.hashCode(), otherCustomerData.hashCode());
  }

  @Test
  void testToString() {
    // Test toString method
    customerData.setAttribute("name", "John Doe");
    assertEquals("CustomerData{attributes={name=John Doe}}", customerData.toString());
  }

  @Test
  void getAttributes() {
    // Test getAttributes method
    HashMap<String, String> attributes = new HashMap<>();
    attributes.put("name", "John Doe");
    attributes.put("age", "30");
    customerData.setAttributes(attributes);
    assertEquals(attributes, customerData.getAttributes());
  }

  @Test
  void setAttributes() {
    // Test setAttributes method
    HashMap<String, String> attributes = new HashMap<>();
    attributes.put("name", "Jane Doe");
    attributes.put("city", "New York");
    customerData.setAttributes(attributes);
    assertEquals(attributes, customerData.getAttributes());
  }
}

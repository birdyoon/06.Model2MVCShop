package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																		"classpath:config/context-aspect.xml",
																		"classpath:config/context-mybatis.xml",
																		"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	
	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("testProdName");
		product.setProdDetail("testtest");
		product.setManuDate("222");
		product.setPrice(333);
		product.setFileName("��⵵");
		
		
		productService.addProduct(product);
		
	//	product = productService.getProduct("testprodNo");

		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testtest", product.getProdDetail());
		Assert.assertEquals("222", product.getManuDate());
		Assert.assertEquals(333, product.getPrice());
		Assert.assertEquals("��⵵", product.getFileName());
	}

	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		//==> �ʿ��ϴٸ�...
		product.setProdName("testProdName");
		product.setProdDetail("testUserName");
		product.setManuDate("222");
		product.setPrice(333);
		product.setFileName("��⵵");
				
		product = productService.getProduct(10040);

		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testtest", product.getProdDetail());
		Assert.assertEquals("222", product.getManuDate());
		Assert.assertEquals(333, product.getPrice());
		Assert.assertEquals("��⵵", product.getFileName());

		Assert.assertNotNull(productService.getProduct(10040));
//		Assert.assertNotNull(productService.getProduct(10118));
	}
	
	//@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = productService.getProduct(10040);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("222", product.getManuDate());
		Assert.assertEquals(333, product.getPrice());
		Assert.assertEquals("��⵵", product.getFileName());

		product.setProdName("change");
		product.setManuDate("444");
		product.setPrice(555);
		product.setFileName("���ֵ�");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10040);
		Assert.assertNotNull(product);
		
		//==> console Ȯ��
		System.out.println(product);
			
		//==> API Ȯ��
		Assert.assertEquals("change", product.getProdName());
		Assert.assertEquals("444", product.getManuDate());
		Assert.assertEquals(555, product.getPrice());
		Assert.assertEquals("���ֵ�", product.getFileName());
	 }
	
			
	 //==>  �ּ��� Ǯ�� �����ϸ�....
	 //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByProdNo() throws Exception{  
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10040");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);    // table SELECT 3���� ���� �� �������� NULL�� ��
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 @Test
	 public void testGetProductListByProdName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("change");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());  //����ð��� ��ȯ?
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
}
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
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																		"classpath:config/context-aspect.xml",
																		"classpath:config/context-mybatis.xml",
																		"classpath:config/context-transaction.xml" })
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
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
		product.setFileName("경기도");
		
		
		productService.addProduct(product);
		
	//	product = productService.getProduct("testprodNo");

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testtest", product.getProdDetail());
		Assert.assertEquals("222", product.getManuDate());
		Assert.assertEquals(333, product.getPrice());
		Assert.assertEquals("경기도", product.getFileName());
	}

	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		//==> 필요하다면...
		product.setProdName("testProdName");
		product.setProdDetail("testUserName");
		product.setManuDate("222");
		product.setPrice(333);
		product.setFileName("경기도");
				
		product = productService.getProduct(10040);

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testtest", product.getProdDetail());
		Assert.assertEquals("222", product.getManuDate());
		Assert.assertEquals(333, product.getPrice());
		Assert.assertEquals("경기도", product.getFileName());

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
		Assert.assertEquals("경기도", product.getFileName());

		product.setProdName("change");
		product.setManuDate("444");
		product.setPrice(555);
		product.setFileName("제주도");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10040);
		Assert.assertNotNull(product);
		
		//==> console 확인
		System.out.println(product);
			
		//==> API 확인
		Assert.assertEquals("change", product.getProdName());
		Assert.assertEquals("444", product.getManuDate());
		Assert.assertEquals(555, product.getPrice());
		Assert.assertEquals("제주도", product.getFileName());
	 }
	
			
	 //==>  주석을 풀고 실행하면....
	 //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
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
	 	
	 	//==> console 확인
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
	 	
		//==> console 확인
	 	System.out.println(list);    // table SELECT 3개만 들어가서 그 나머지는 NULL로 뜸
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
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
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());  //현재시간을 반환?
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
}
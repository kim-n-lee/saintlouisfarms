package org.liftoff.saintlouisfarms.data;


import org.liftoff.saintlouisfarms.models.BasketItem;
import org.liftoff.saintlouisfarms.models.DTO.ShoppingBasketDTO;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query(value = "select * from product left join productdetails on product.productDetails_id=productdetails.id where  product.user_id = ?1", nativeQuery = true)
    List<Product> findProductById(int id);
@Query(value = "select * from product left join productdetails on product.productDetails_id=productdetails.id where productdetails.status=true and product.user_id = ?1", nativeQuery = true)
    List<Product> findProductByStatus(int ids);

@Query(value = "delete  from product where id= ?1", nativeQuery = true)
    List<Product>deleteProductById(int id);

@Query(value = "select *  from product where name= ?1", nativeQuery = true)

List<Product>findNameOfProductBy(String name);

    @Query(value = "SELECT * FROM farm.product p left join productdetails d on p.productDetails_id=d.id left join user u on p.user_id=u.id where d.status=1 order by u.farmName", nativeQuery = true)
    List<Product> findAllProduct();
    @Query(value = "select * from product left join user on product.user_id=user.id  left join productdetails  on product.productDetails_id=productdetails.id where user.farmName= ?1 and productdetails.status=1", nativeQuery = true)
    List<Product> findByNameOfFarmName( String farmName);



    @Query(value = "select * from product" +
            " left join productcategory on product.productCategory_id=productcategory.id" +
            " left join measurementcategory on product.measurementCategory_id=measurementcategory.id " +
            " left join productdetails on product.productDetails_id=productdetails.id " +
            "where (product.name like %?1% or productcategory.name like %?1% " +
            "or measurementcategory.name like %?1%  or productdetails.status like %?1% " +
            "or productdetails.price like %?1% " +
            "or productdetails.quantity like %?1%  " +
            "or productdetails.description  like %?1% )and product.user_id=?2",nativeQuery = true)
    List<Product> searchByInfo(String name,int id );

    @Query(value = "select * from product" +
            " left join productcategory on product.productCategory_id=productcategory.id" +
            " left join measurementcategory on product.measurementCategory_id=measurementcategory.id " +
            " left join productdetails on product.productDetails_id=productdetails.id " +
            "where (product.name like %?1% or productcategory.name like %?1% " +
            "or measurementcategory.name like %?1% " +
            "or productdetails.price like %?1% " +
            "or productdetails.quantity like %?1%  " +
            "or productdetails.description  like %?1% )and product.user_id=?2 and productdetails.status=1",nativeQuery = true)
    List<Product> searchOnAvailableProducts(String name,int id );

    // query to search for product in client side
    @Query(value = "select * from product " +
            " left join productcategory on product.productCategory_id=productcategory.id " +
            " left join measurementcategory on product.measurementCategory_id=measurementcategory.id " +
            " left join productdetails on product.productDetails_id=productdetails.id " +
            " left join user on product.user_id=user.id"+
            " where (product.name like %?1% or productcategory.name like %?1% " +
            " or measurementcategory.name like %?1% " +
            " or productdetails.price like %?1% " +
            " or productdetails.description  like %?1% )and user.farmName=?2 and productdetails.status=1",nativeQuery = true)
    List<Product> searchByFarm(String info, String farmName);

}
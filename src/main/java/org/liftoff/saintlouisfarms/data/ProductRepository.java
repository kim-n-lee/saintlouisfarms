package org.liftoff.saintlouisfarms.data;


import org.liftoff.saintlouisfarms.models.BasketItem;
import org.liftoff.saintlouisfarms.models.DTO.ShoppingBasketDTO;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query(value = "select * from product left join productdetails on product.productDetails_id=productdetails.id where  product.user_id = ?1 and product.isDeleted=0", nativeQuery = true)
    List<Product> findProductById(int id);
    @Query(value = "select * from product left join productdetails on product.productDetails_id=productdetails.id where productdetails.status=true and product.user_id = ?1 and product.isDeleted=0 ", countQuery = "SELECT COUNT(*) FROM product left join productdetails on product.productDetails_id=productdetails.id where productdetails.status=true and product.user_id = ?1 and product.isDeleted=0 ", nativeQuery = true)
    Page<Product> findProductByStatus(int userId, Pageable pageable);


    @Query(value = "delete  from product where id= ?1", nativeQuery = true)
    List<Product>deleteProductById(int id);
@Query(value = "select *  from product where name= ?1 and isDeleted=0 ", nativeQuery = true)
List<Product>findNameOfProductBy(String name);

    @Query(value = "SELECT * FROM product p left join productdetails d on p.productDetails_id=d.id left join user u on p.user_id=u.id where d.status=1 order by u.farmName and product.isDeleted=0 ", nativeQuery = true)
    List<Product> findAllProduct();
    @Query(value = "select * from product left join user on product.user_id=user.id  left join productdetails  on product.productDetails_id=productdetails.id where user.farmName= ?1 and productdetails.status=1 and product.isDeleted=0 ", countQuery = "SELECT COUNT(*) FROM product left join user on product.user_id=user.id  left join productdetails  on product.productDetails_id=productdetails.id where user.farmName= ?1 and productdetails.status=1 and product.isDeleted=0 ", nativeQuery = true)
    Page<Product> findByNameOfFarmName(String farmName, Pageable pageable);
    @Query(value = "select * from product left join user on product.user_id=user.id  left join productdetails  on product.productDetails_id=productdetails.id where user.farmName= ?1 and productdetails.status=1 and product.isDeleted=0 ", nativeQuery = true)
    List<Product> findByNameOfFarmNames( String farmName);



    @Query(value = "select * from product" +
            " left join productcategory on product.productCategory_id=productcategory.id" +
            " left join measurementcategory on product.measurementCategory_id=measurementcategory.id " +
            " left join productdetails on product.productDetails_id=productdetails.id " +
            "where (product.name like %?1% or productcategory.name like %?1% " +
            "or measurementcategory.name like %?1%  or productdetails.status like %?1% " +
            "or productdetails.price like %?1% " +
            "or productdetails.quantity like %?1%  " +
            "or productdetails.description  like %?1% )and product.user_id=?2 and product.isDeleted=0 ",nativeQuery = true)
    List<Product> searchByInfo(String name,int id );

    @Query(value = "select * from product" +
            " left join productcategory on product.productCategory_id=productcategory.id" +
            " left join measurementcategory on product.measurementCategory_id=measurementcategory.id " +
            " left join productdetails on product.productDetails_id=productdetails.id " +
            "where (product.name like %?1% or productcategory.name like %?1% " +
            "or measurementcategory.name like %?1% " +
            "or productdetails.price like %?1% " +
            "or productdetails.quantity like %?1%  " +
            "or productdetails.description  like %?1% )and product.user_id=?2 and productdetails.status=1 and product.isDeleted=0 ",nativeQuery = true)
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
            " or productdetails.description  like %?1% )and user.farmName=?2 and productdetails.status=1 and product.isDeleted=0 ",nativeQuery = true)

    List<Product> searchByFarm(String info, String farmName);

    @Query(value = "SELECT * FROM product " +
            "LEFT JOIN productcategory ON product.productCategory_id = productcategory.id " +
            "LEFT JOIN measurementcategory ON product.measurementCategory_id = measurementcategory.id " +
            "LEFT JOIN productdetails ON product.productDetails_id = productdetails.id " +
            "LEFT JOIN user ON product.user_id = user.id " +
            "WHERE (product.name LIKE %?1% OR productcategory.name LIKE %?1% " +
            "OR measurementcategory.name LIKE %?1% " +
            "OR productdetails.price LIKE %?1% " +
            "OR productdetails.description LIKE %?1%) " +
            "AND user.farmName = ?2 AND productdetails.status = 1",
            countQuery = "SELECT COUNT(*) FROM product " +
                    "LEFT JOIN productcategory ON product.productCategory_id = productcategory.id " +
                    "LEFT JOIN measurementcategory ON product.measurementCategory_id = measurementcategory.id " +
                    "LEFT JOIN productdetails ON product.productDetails_id = productdetails.id " +
                    "LEFT JOIN user ON product.user_id = user.id " +
                    "WHERE (product.name LIKE %?1% OR productcategory.name LIKE %?1% " +
                    "OR measurementcategory.name LIKE %?1% " +
                    "OR productdetails.price LIKE %?1% " +
                    "OR productdetails.description LIKE %?1%) " +
                    "AND user.farmName = ?2 AND productdetails.status = 1 and product.isDeleted=0",
            nativeQuery = true)
    Page<Product> searchByFarmm(String info, String farmName, Pageable pageable);

    @Query(value = "SELECT * FROM product" +
            " LEFT JOIN productcategory ON product.productCategory_id=productcategory.id" +
            " LEFT JOIN measurementcategory ON product.measurementCategory_id=measurementcategory.id" +
            " LEFT JOIN productdetails ON product.productDetails_id=productdetails.id" +
            " WHERE (product.name LIKE %?1% OR productcategory.name LIKE %?1%" +
            " OR measurementcategory.name LIKE %?1%" +
            " OR productdetails.price LIKE %?1%" +
            " OR productdetails.quantity LIKE %?1%" +
            " OR productdetails.description LIKE %?1%)" +
            " AND product.user_id=?2 AND productdetails.status=1",
            countQuery = "SELECT COUNT(*) FROM product" +
                    " LEFT JOIN productcategory ON product.productCategory_id=productcategory.id" +
                    " LEFT JOIN measurementcategory ON product.measurementCategory_id=measurementcategory.id" +
                    " LEFT JOIN productdetails ON product.productDetails_id=productdetails.id" +
                    " WHERE (product.name LIKE %?1% OR productcategory.name LIKE %?1%" +
                    " OR measurementcategory.name LIKE %?1%" +
                    " OR productdetails.price LIKE %?1%" +
                    " OR productdetails.quantity LIKE %?1%" +
                    " OR productdetails.description LIKE %?1%)" +
                    " AND product.user_id=?2 AND productdetails.status=1 and product.isDeleted=0",
            nativeQuery = true)
    Page<Product> searchOnAvailableProducts(String name, int userId, Pageable pageable);
}
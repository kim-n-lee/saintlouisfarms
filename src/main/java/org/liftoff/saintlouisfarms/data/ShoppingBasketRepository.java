package org.liftoff.saintlouisfarms.data;


import org.liftoff.saintlouisfarms.models.DTO.ShoppingBasketDTO;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingBasketRepository extends CrudRepository<ShoppingBasket, Integer> {
    @Query(value = "select * from shoppingbasket left join product on shoppingbasket.products_id=product.id left join client on shoppingbasket.client_id=client.id  where shoppingbasket.products_id= ?1 and shoppingbasket.client_id= ?2 ", nativeQuery = true)
    ShoppingBasket findByClientAndProduct(int id, int id2);

    @Query(value = "select * from shoppingbasket " +
            " left join basketitem on basketitem.shoppingBasket_id=shoppingbasket.id " +
            " left join shoppingbasket_basketitems on basketitem.shoppingBasketAvailable_id=shoppingbasket_basketitems.basketItems_id "+
            " left join product on basketitem.product_id=product.id " +
            " left join productcategory on product.productCategory_id=productcategory.id " +
            " left join measurementcategory on product.measurementCategory_id=measurementcategory.id " +
            " left join productdetails on product.productDetails_id=productdetails.id " +
            " left join user on product.user_id=user.id"+
            " where (product.name like %?1% or productcategory.name like %?1% " +
            " or measurementcategory.name like %?1% " +
            " or productdetails.price like %?1% " +
            " or productdetails.description  like %?1% )and user.farmName=?2 and productdetails.status=1",nativeQuery = true)
    List<ShoppingBasket> searchByFarm(String info, String farmName);
@Query(value = " select * from shoppingbasket " +
        " left join   basketitem on basketitem.shoppingBasket_id=shoppingbasket.id " +
        " left join client on shoppingbasket.client_id=client.id " +
        " left join product on basketitem.product_id=product.id " +
        " left join productdetails on productdetails.id=product.productDetails_id " +
        " left join user on product.user_id=user.id " +
        " where basketitem.farmOrderItem_id is null and shoppingbasket.client_id=?1 and basketitem.quantity!=0 and user.farmName=?2 ",nativeQuery = true)
 List<ShoppingBasket> theCart(int id, String farmName);

@Query(value = "select *from shoppingbasket where client_id=?1",nativeQuery = true)
ShoppingBasket findAboutClientCart(int id);

    @Query(value = " SELECT * FROM shoppingbasket " +
            " left join basketitem on shoppingbasket.id=basketitem.shoppingBasket_id " +
            " left join product on basketitem.product_id=product.id " +
        " where shoppingbasket.id=?1 and shoppingbasket.client_id=?2 and product.name=?3",nativeQuery = true)
    Optional<ShoppingBasket> findByIdAndClient(int basketId, int id,String productName);

//@Query(value = "select *from shoppingbasket  " +
//        " left join   basketitem on basketitem.shoppingBasket_id=shoppingbasket.id " +
//        " where basketitem.id=?1",nativeQuery = true)
//    List<ShoppingBasket> findByBasketId(int basketId);
}

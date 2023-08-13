package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.BasketItem;
import org.liftoff.saintlouisfarms.models.FarmOrder;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketItemRepository extends CrudRepository<BasketItem, Integer> {
    @Query(value = "select *,product.name as name ,SUM(basketitem.quantity) as q , (SUM(basketitem.quantity)*productdetails.price)as tottalPrice from shoppingbasket \n" +
            "left join client on shoppingbasket.client_id=client.id\n" +
            "left join basketitem on shoppingbasket.id=basketitem.shoppingBasket_id\n" +
            "left join product on basketitem.product_id=product.id\n" +
            "left join productdetails on product.productDetails_id=productdetails.id\n" +
            "left join user on user.id=product.user_id\n" +
            "where client.id=?1 and user.farmName=?2 group by product.id" ,nativeQuery = true)
    List<ShoppingBasket> findShoopingBasket(int clientId, String farmName);

    @Query(value = " select * from basketitem " +
            " left join  shoppingbasket  on basketitem.shoppingBasket_id=shoppingbasket.id " +
            " left join client on shoppingbasket.client_id=client.id " +
            " left join product on basketitem.product_id=product.id " +
            " left join productdetails on productdetails.id=product.productDetails_id " +
            " left join user on product.user_id=user.id " +
            " where basketitem.farmOrderItem_id is null and shoppingbasket.client_id=?1 and basketitem.quantity!=0 and user.farmName=?2 ",nativeQuery = true)
    List<BasketItem> findTheCart(int id, String farmName);
@Query(value = "SELECT * FROM  basketitem  " +
        " left join farmorder on basketitem.farmOrderItem_id=farmorder.id " +
        " where basketitem.farmOrderItem_id=?1 and farmorder.client_id=?2" ,nativeQuery = true)
    List<BasketItem> findAllBasketAsoociatedWithOrder(int orderId, int id);

@Query(value = " select basketitem.quantity from basketitem " +
        "left join product on basketitem.product_id=product.id=?1",nativeQuery = true)
    int checkQuantity(int id);
    @Query(value = " select * from basketitem  " +
            " left join shoppingbasket on basketitem.shoppingBasket_id=shoppingbasket.id " +
            " where basketitem.product_id=?1 and shoppingbasket.client_id=?2",nativeQuery = true)
    BasketItem findBasketForProduct(int id,int id2);

    @Query(value = " select * from basketitem  where basketitem.product_id=?1 ",nativeQuery = true)
    BasketItem findBasketForProductguest(int id);

    @Query
    List<BasketItem> findByShoppingBasket(ShoppingBasket shoppingBasket);
}

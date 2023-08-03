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





}

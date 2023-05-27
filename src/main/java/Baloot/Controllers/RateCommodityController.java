package Baloot.Controllers;



import Baloot.Commands.RateCommodity;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Commodity;
import Baloot.Entity.Rate;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.InvalidRateScore;
import Baloot.Exception.UserNotFound;
import Baloot.Model.RateModel;
import Baloot.Model.UserByIdModel;
import Baloot.Repository.ReteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rateCommodity")
public class RateCommodityController {
    @Autowired
    private final ReteRepository reteRepository;
    
    private void handle(RateModel model) throws Exception, UserNotFound, CommodityNotFound {
        if (model.rate < 1 || model.rate > 10) {
            throw new InvalidRateScore();
        }
        ContextManager.getInstance().getUser(model.username);
        Commodity commodity = ContextManager.getInstance().getCommodity(model.commodityId);
        Rate rate = new Rate(commodity, model.username, model.rate);
        reteRepository.save(rate);
        List<Rate> rates = (List<Rate>) reteRepository.findAllByCommodity(commodity);
        commodity.addRate(rates);
        ContextManager.getInstance().putCommodity(commodity.getId(), commodity);
    }

    @PostMapping("/{commodityId}")
    public void rateCommodity(@PathVariable Integer commodityId, @RequestParam("comment_id") Integer rate) {
        if (Authentication.isNotAuthenticated()) return;
        RateModel model = new RateModel();
        model.rate = rate;
        model.commodityId = commodityId;
        model.username = UserContext.username;
        try {
            handle(model);
        } catch (UserNotFound | CommodityNotFound | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

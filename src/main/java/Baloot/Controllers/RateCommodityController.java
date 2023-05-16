package Baloot.Controllers;


import Baloot.Commands.Payment;
import Baloot.Commands.RateCommodity;
import Baloot.Context.UserContext;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.UserNotFound;
import Baloot.Model.RateModel;
import Baloot.Model.UserByIdModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/rateCommodity")
public class RateCommodityController {
    @PostMapping("/{commodityId}")
    public void rateCommodity(@PathVariable Integer commodityId, @RequestParam("comment_id") Integer rate) {
        if (Authentication.isNotAuthenticated()) return;
        RateCommodity command = new RateCommodity();
        RateModel model = new RateModel();
        model.rate = rate;
        model.commodityId = commodityId;
        model.username = UserContext.username;
        try {
            command.handle(model);
        } catch (UserNotFound | CommodityNotFound | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

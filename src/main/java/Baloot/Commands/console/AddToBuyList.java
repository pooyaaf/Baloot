package Baloot.Commands.console;

import Baloot.AcceptMethod;
import Baloot.Commands.Command;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Model.BuyModel;
import Baloot.RequestMethod;
import Baloot.Route;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.UserNotFound;

@Route("addToBuyList")
public class AddToBuyList extends Command {
    @AcceptMethod(RequestMethod.GET)
    public String handle(BuyModel input) throws Exception, UserNotFound, CommodityNotFound, CommodityNotInStuck {


        return "commodity was added to buy list successfully";
    }
}

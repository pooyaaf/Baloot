package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.ProviderNotFound;
import Baloot.Model.CommodityModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("addCommodity")
public class addCommodity extends Command {
    @AcceptMethod(RequestMethod.GET)
    public String handle(CommodityModel model) throws Exception, ProviderNotFound {
        ContextManager.getProvider(model.providerId);
        Commodity commodity = new Commodity(model);
        ContextManager.putCommodity(model.id, commodity);
        return "commodity added successfully";
    }
}

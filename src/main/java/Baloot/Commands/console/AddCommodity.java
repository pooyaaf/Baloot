package Baloot.Commands.console;

import Baloot.AcceptMethod;
import Baloot.Commands.Command;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.ProviderNotFound;
import Baloot.Model.view.CommodityModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("addCommodity")
public class AddCommodity extends Command {
    @AcceptMethod(RequestMethod.GET)
    public String handle(CommodityModel model) throws Exception, ProviderNotFound {
        ContextManager.getProvider(model.providerId);
        Commodity commodity = new Commodity(model);
        ContextManager.putCommodity(model.id, commodity);
        return "commodity added successfully";
    }
}

package Baloot.Commands.console;

import Baloot.AcceptMethod;
import Baloot.Commands.Command;
import Baloot.Context.ContextManager;
import Baloot.Entity.Provider;
import Baloot.Model.ProviderModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("addProvider")
public class AddProvider extends Command {
    @AcceptMethod(RequestMethod.GET)
    public String handle(ProviderModel model) throws Exception {
        Provider provider = new Provider(model);
        ContextManager.putProvider(model.id, provider);
        return "provider added successfully";
    }
}

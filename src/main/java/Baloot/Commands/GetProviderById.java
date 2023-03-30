package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Provider;
import Baloot.Exception.ProviderNotFound;
import Baloot.Model.ProviderByIdModel;
import Baloot.View.ProviderViewModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("providers/{provider_id}")
public class GetProviderById extends Command{
    @AcceptMethod(RequestMethod.GET)
    public ProviderViewModel handle(ProviderByIdModel input) throws Exception, ProviderNotFound {
        Provider provider = ContextManager.getInstance().getProvider(Integer.parseInt(input.provider_id));
        return provider.GetProviderViewModel();
    }
}

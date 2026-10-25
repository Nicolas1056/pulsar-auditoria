package com.metrofico.pulsar.api.hub;

public record HubNavigation(String category, String detailId, double scroll) {
   private static final String CATEGORY_KEY = "hub.lastCategory";
   private static final String DETAIL_KEY = "hub.lastDetail";
   private static final String SCROLL_KEY = "hub.lastScroll";
   private static final String DEFAULT_CATEGORY = "Combate";

   public static HubNavigation last() {
      ClientOptions options = ClientOptions.get();
      String detailId = options.string("hub.lastDetail", "");
      String category = options.string("hub.lastCategory", "Combate");
      return !hasOptions(category)
         ? new HubNavigation("Combate", null, 0.0)
         : new HubNavigation(category, detailId.isEmpty() ? null : detailId, Math.max(0.0, options.dbl("hub.lastScroll", 0.0)));
   }

   private static boolean hasOptions(String category) {
      for (HubOption option : HubOptions.all()) {
         if (option.category().equals(category)) {
            return true;
         }
      }

      return false;
   }

   public HubOption detail() {
      if (this.detailId == null) {
         return null;
      } else {
         for (HubOption option : HubOptions.all()) {
            if (option.id().equals(this.detailId)) {
               return option;
            }
         }

         return null;
      }
   }

   public void save() {
      ClientOptions options = ClientOptions.get();
      options.set("hub.lastCategory", this.category);
      options.set("hub.lastDetail", this.detailId == null ? null : this.detailId);
      options.set("hub.lastScroll", this.scroll);
   }
}

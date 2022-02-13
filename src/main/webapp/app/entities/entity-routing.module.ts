import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'menu-group',
        data: { pageTitle: 'jewelsysApp.menuGroup.home.title' },
        loadChildren: () => import('./menu-group/menu-group.module').then(m => m.MenuGroupModule),
      },
      {
        path: 'menu',
        data: { pageTitle: 'jewelsysApp.menu.home.title' },
        loadChildren: () => import('./menu/menu.module').then(m => m.MenuModule),
      },
      {
        path: 'role-menu-map',
        data: { pageTitle: 'jewelsysApp.roleMenuMap.home.title' },
        loadChildren: () => import('./role-menu-map/role-menu-map.module').then(m => m.RoleMenuMapModule),
      },
      {
        path: 'data-category',
        data: { pageTitle: 'jewelsysApp.dataCategory.home.title' },
        loadChildren: () => import('./data-category/data-category.module').then(m => m.DataCategoryModule),
      },
      {
        path: 'gold-type',
        data: { pageTitle: 'jewelsysApp.goldType.home.title' },
        loadChildren: () => import('./gold-type/gold-type.module').then(m => m.GoldTypeModule),
      },
      {
        path: 'gold-item-group',
        data: { pageTitle: 'jewelsysApp.goldItemGroup.home.title' },
        loadChildren: () => import('./gold-item-group/gold-item-group.module').then(m => m.GoldItemGroupModule),
      },
      {
        path: 'gold-item',
        data: { pageTitle: 'jewelsysApp.goldItem.home.title' },
        loadChildren: () => import('./gold-item/gold-item.module').then(m => m.GoldItemModule),
      },
      {
        path: 'gold-price-rate',
        data: { pageTitle: 'jewelsysApp.goldPriceRate.home.title' },
        loadChildren: () => import('./gold-price-rate/gold-price-rate.module').then(m => m.GoldPriceRateModule),
      },
      {
        path: 'gems-type',
        data: { pageTitle: 'jewelsysApp.gemsType.home.title' },
        loadChildren: () => import('./gems-type/gems-type.module').then(m => m.GemsTypeModule),
      },
      {
        path: 'gems-item',
        data: { pageTitle: 'jewelsysApp.gemsItem.home.title' },
        loadChildren: () => import('./gems-item/gems-item.module').then(m => m.GemsItemModule),
      },
      {
        path: 'gems-price-rate',
        data: { pageTitle: 'jewelsysApp.gemsPriceRate.home.title' },
        loadChildren: () => import('./gems-price-rate/gems-price-rate.module').then(m => m.GemsPriceRateModule),
      },
      {
        path: 'shop-info',
        data: { pageTitle: 'jewelsysApp.shopInfo.home.title' },
        loadChildren: () => import('./shop-info/shop-info.module').then(m => m.ShopInfoModule),
      },
      {
        path: 'counter-info',
        data: { pageTitle: 'jewelsysApp.counterInfo.home.title' },
        loadChildren: () => import('./counter-info/counter-info.module').then(m => m.CounterInfoModule),
      },
      {
        path: 'mortgage-entry',
        data: { pageTitle: 'jewelsysApp.mortgageEntry.home.title' },
        loadChildren: () => import('./mortgage-entry/mortgage-entry.module').then(m => m.MortgageEntryModule),
      },
      {
        path: 'mortgage-item',
        data: { pageTitle: 'jewelsysApp.mortgageItem.home.title' },
        loadChildren: () => import('./mortgage-item/mortgage-item.module').then(m => m.MortgageItemModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

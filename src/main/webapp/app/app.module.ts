import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DansLeMealSharedModule } from 'app/shared/shared.module';
import { DansLeMealCoreModule } from 'app/core/core.module';
import { DansLeMealAppRoutingModule } from './app-routing.module';
import { DansLeMealHomeModule } from './home/home.module';
import { DansLeMealEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    DansLeMealSharedModule,
    DansLeMealCoreModule,
    DansLeMealHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DansLeMealEntityModule,
    DansLeMealAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class DansLeMealAppModule {}

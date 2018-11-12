import './polyfills.ts';

import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {enableProdMode} from '@angular/core';
import {environment} from './environments/environment';
import {AppModule} from './app/';
import {hmrBootstrap} from "./hmr";

const bootstrap = () => platformBrowserDynamic().bootstrapModule(AppModule);

if (environment.production) {
    enableProdMode();
    bootstrap();
    window.console.log = function(){};
} else if (environment.hmr) {
    console.log("You are in HMR mode");
    if (module['hot']) {
        hmrBootstrap(module, bootstrap);
    } else {
        console.error('HMR is not enabled for webpack-dev-server!');
        console.log('Are you using the --hmr flag for ng serve?');
    }
} else {
    bootstrap();
    console.log("You are in Dev mode");
}

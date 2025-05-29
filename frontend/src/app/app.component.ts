import { Component } from '@angular/core';
import { Router, RouterOutlet, NavigationEnd } from '@angular/router';
import { HeaderComponent } from './components/shared/header/header.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
  exibirLayout: boolean = true;

  constructor(private router: Router) {}

  ngOnInit() {
    this.router.events
    .pipe(filter(event => event instanceof NavigationEnd))
    .subscribe((event: NavigationEnd) => {
      if(event.urlAfterRedirects == '/cadastro' || event.urlAfterRedirects == '/'){
        this.exibirLayout = false;
      } else {
        this.exibirLayout = true;
      }
      // console.log('Nova rota e exibir:', event.urlAfterRedirects, this.exibirLayout);
    });
  }
}

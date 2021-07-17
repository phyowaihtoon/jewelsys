import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MenuGroupDetailComponent } from './menu-group-detail.component';

describe('Component Tests', () => {
  describe('MenuGroup Management Detail Component', () => {
    let comp: MenuGroupDetailComponent;
    let fixture: ComponentFixture<MenuGroupDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MenuGroupDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ menuGroup: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MenuGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MenuGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load menuGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.menuGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

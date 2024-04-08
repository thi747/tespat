import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FornecedorDetailComponent } from './fornecedor-detail.component';

describe('Fornecedor Management Detail Component', () => {
  let comp: FornecedorDetailComponent;
  let fixture: ComponentFixture<FornecedorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FornecedorDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FornecedorDetailComponent,
              resolve: { fornecedor: () => of({ nome: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FornecedorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FornecedorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fornecedor on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FornecedorDetailComponent);

      // THEN
      expect(instance.fornecedor).toEqual(expect.objectContaining({ nome: 'ABC' }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});

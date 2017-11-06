import { ComponentePagoPage } from './app.po';

describe('componente-pago App', () => {
  let page: ComponentePagoPage;

  beforeEach(() => {
    page = new ComponentePagoPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});

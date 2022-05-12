import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[jhiNumberonly]'
})
export class NumberonlyDirective {

  // + ( One or More )
  // * ( zero or more )
  // ^  ( beginning of line )  , $ ( end of line )
  // const re = /^(?:\d{3}|\(\d{3}\))([-\/\.])\d{3}\1\d{4}$/; e.g 095-333-4444 or 095/333/4444

  private specialKeys: Array<string> = ['Backspace', 'Delete', 'ArrowLeft', 'ArrowRight'];

  constructor(private elementRef: ElementRef) { }

  @HostListener('keydown', ['$event']) onKeyDown(event: KeyboardEvent):void {
    if (!this.specialKeys.includes(event.key)) {
      const regExpNum: RegExp = new RegExp(/^[0-9]+$/g);
      const inputValue: string = this.elementRef.nativeElement.value.concat(event.key);
      if (inputValue && regExpNum.exec(String(inputValue)) === null) {
        event.preventDefault();
      }
    }
  }

}

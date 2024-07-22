// <link rel="stylesheet" href="./Button/ButtonStyle.css" />

const template = document.createElement("template")
template.innerHTML = `
    <link rel="stylesheet" href="./WebComponents/Button/ButtonStyle.css" />
    <div class="Button">
        <slot/>
    </div>
`

class Button extends HTMLElement {


    constructor() {
        super()
        const shadow = this.attachShadow({ mode: 'open' })
        shadow.append(template.content.cloneNode(true))
    }

    static observedAttributes = ["Text"];
}

customElements.define("custom-button", Button);
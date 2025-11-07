import {ctx, canvas, X, Y, R} from "./script.js";

export function draw() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
        
    draw_IV();
    draw_II();
    draw_III();
    makeXOY();
    drawPoint();
}

function drawPoint() {
    const scale = 30;
    let x = Number(X.value)*scale + 190;
    let y = -Number(Y().value)*scale + 190;

    ctx.beginPath();
    ctx.strokeStyle = "#4c835a";
    ctx.fillStyle = "#4c835a";

    moveTo(x, y);
    ctx.arc(x, y, 3, 0, Math.PI*2, false);
    ctx.fill();

    ctx.closePath();
    ctx.stroke();
}

function draw_IV() {
    let r = R.value;
    const scale = 30;

    ctx.beginPath();
    ctx.strokeStyle = "#b3dbbd";
    ctx.fillStyle = "#b3dbbde5";
    ctx.lineWidth = 0.8

    ctx.moveTo(190, 190);
    ctx.arc(190, 190, r*scale/2, 0, Math.PI/2, false);
    ctx.fill();

    ctx.closePath();
    ctx.stroke();
}

function draw_II() {
    let r = R.value;
    const scale = 30;
    
    ctx.beginPath();
    ctx.strokeStyle = "#b3dbbd";
    ctx.fillStyle = "#b3dbbde5";
    ctx.lineWidth = 0.8
    
    ctx.moveTo(190, 190);
    ctx.lineTo(190 - r*scale/2, 190);
    ctx.lineTo(190, 190 - r*scale/2);
    ctx.lineTo(190, 190);
    ctx.fill();
    
    ctx.closePath();
    ctx.stroke();    
}

function draw_III() {
    let r = R.value;
    const scale = 30;
    
    ctx.beginPath();
    ctx.strokeStyle = "#b3dbbd";
    ctx.fillStyle = "#b3dbbde5";
    ctx.lineWidth = 0.8

    ctx.fillRect(190, 190, -r*scale, r*scale);

    ctx.closePath();
    ctx.stroke();
}

function makeXOY() {

    ctx.beginPath();
    ctx.strokeStyle = "#000000ff";
    ctx.fillStyle = "#000000ff";
    ctx.lineWidth = 0.8

    ctx.moveTo(190, 190);
    ctx.lineTo(190, 380);
    ctx.moveTo(190, 190);
    ctx.lineTo(190, 0);
    ctx.lineTo(198, 7);
    ctx.moveTo(190, 0);
    ctx.lineTo(182, 7);
    ctx.fillText("Y", 210, 9);

    ctx.moveTo(190, 190);
    ctx.lineTo(0, 190);
    ctx.moveTo(190, 190);
    ctx.lineTo(380, 190);
    ctx.lineTo(373, 198);
    ctx.moveTo(380, 190);
    ctx.lineTo(373, 182);
    ctx.fillText("X", 371, 170);

    ctx.closePath();
    ctx.stroke();

    for (let i = 30; i < 180; i += 30) {
        makeOX(i/30, 190 + i, 190);
        makeOX(-i/30, 190 - i, 190);
    }

    for (let i = 30; i < 180; i += 30) {
        makeOY(-i/30, 190, 190 + i);
        makeOY(i/30, 190, 190 - i);
    }
}

function makeOX(i, x, y) {
    ctx.beginPath();
    ctx.strokeStyle = "#000000ff";
    ctx.fillStyle = "#000000ff";
    ctx.lineWidth = 0.8

    ctx.moveTo(x, y);
    ctx.lineTo(x, y+5);
    ctx.moveTo(x, y);
    ctx.lineTo(x, y-5);
    ctx.moveTo(x, y);
    ctx.fillText(i, x-3, y+17);

    ctx.closePath();
    ctx.stroke();
}

function makeOY(i, x, y) {
    ctx.beginPath();
    ctx.strokeStyle = "#000000ff";
    ctx.fillStyle = "#000000ff";
    ctx.lineWidth = 0.8

    ctx.moveTo(x, y);
    ctx.lineTo(x+5, y);
    ctx.moveTo(x, y);
    ctx.lineTo(x-5, y);
    ctx.moveTo(x, y);
    ctx.fillText(i, x+12, y+4);

    ctx.closePath();
    ctx.stroke();
}
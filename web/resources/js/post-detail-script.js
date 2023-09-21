document.getElementById("post-title").addEventListener("change",()=> {
    var form= document.createElement("form");
    form.setAttribute("method","Post");
    form.setAttribute("action","postDetail");
    var input= document.createElement("input");
    input.setAttribute("type","hidden");
    input.setAttribute("name","postTitle");
    input.setAttribute("value",document.getElementById("post-title").value);
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
});
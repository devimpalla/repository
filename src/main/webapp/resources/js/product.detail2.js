document.getElementById('delBtn').addEventListener('click', () =>{
    const delForm = document.getElementById('delForm');
    delForm.submit();
});
async function postCommentToServer(cmtData) {
    try {
        const url = "/cmtCtrl/post";
        const config = {
            method: 'post',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}
document.getElementById('cmtPostBtn').addEventListener('click', () =>{
    const cmtText = document.getElementById('cmtText').value;
    if(cmtText == null || cmtText == ''){
        alert('댓글 내용을 입력해 주세요!');
        return false;
    }else {
        let cmtData = {
            pno : document.querySelector('#delForm input[name=pno]').value,
            writer : document.getElementById('cmtWriter').innerText,
            content : cmtText
        };
        postCommentToServer(cmtData).then(result => {
            // console.log(result);
            printCommentList(cmtData.pno);
        });
    }
});
async function spreadCommentFromServer(pno) {
    try {
        const resp = await fetch('/cmtCtrl/list/'+pno);
        const cmtList = await resp.json();
        return await cmtList;
    } catch (error) {
        console.log(error);
    }
}
function printCommentList(pno){
    spreadCommentFromServer(pno).then(result => {
        console.log(result);

        if(result.length > 0){
            let ul = document.getElementById('cmtListArea');
            ul.innerHTML = '';
            for (let cvo of result) {
                <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                <div class="fw-bold">writer</div>
                Content for Comment
                </div>
                <span class="badge bg-secondary rounded-pill">modAt</span>&nbsp;
                <button type="button" class="btn btn-outline-warning btn-sm py-0">E</button>&nbsp;
                <button type="button" class="btn btn-outline-danger btn-sm py-0">X</button>
                </li>
            }

        }else {
            console.log('Comment List is empty');
        }
    });
}
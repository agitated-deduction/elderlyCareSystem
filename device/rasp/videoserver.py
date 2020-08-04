# coding = utf-8
from flask import Flask, render_template , url_for

app = Flask(__name__)


@app.route('/')
def hello_world():
    # return 'Hello World!'
    return render_template('img_static.html')
    

# @app.route('/download/<path:filename>')
# def download(filename):
#     if not authentication():
#         abort(404)

#     redirect_path = '/download_file/' + filename

#     response = make_response("")
#     response.headers["X-Accel-Redirect"] = redirect_path
#     response.headers["Content-Type"] = mimetypes.guess_type(filename)
#     return response

if __name__ == '__main__':
    app.run(host='192.168.1.19', port = 5000)